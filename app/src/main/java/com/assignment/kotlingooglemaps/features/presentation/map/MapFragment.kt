package com.assignment.kotlingooglemaps.features.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import com.assignment.kotlingooglemaps.R
import com.assignment.kotlingooglemaps.core.exception.Failure
import com.assignment.kotlingooglemaps.core.extension.*
import com.assignment.kotlingooglemaps.features.presentation.model.VehicleView
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.fragment_map.*
import timber.log.Timber

/**
 * Created by danieh on 01/08/2019.
 */
class MapFragment : BaseMapFragment() {

    companion object {
        private val TAG = MapFragment::class.java.simpleName
    }

    private lateinit var viewModel: MapViewModel

    private var clusterManager: ClusterManager<VehicleView>? = null
    private var previousCameraPosition: Array<CameraPosition?> = arrayOfNulls(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setHasOptionsMenu(true)

        viewModel = viewModel(viewModelFactory) {
            observe(vehicleList, ::onVehiclesFetched)
            failure(failure, ::showError)
        }
    }

    override fun setupDone() {
        progress_vehicles.gone()

        // Setup clusterManager
        clusterManager = ClusterManager(context, map)
        map.setOnCameraIdleListener {
            val position = map.cameraPosition
            previousCameraPosition[0]?.let {
                if (it.zoom != position.zoom) {
                    previousCameraPosition[0] = map.cameraPosition
                }
            }
            clusterManager?.cluster()
        }
        clusterManager?.let {
            it.renderer = VehicleClusterRenderer(context, map, it)

            it.setOnClusterClickListener {
                // if true, do not move camera
                false
            }

            it.setOnClusterItemClickListener {
                // if true, click handling stops here and do not show info view, do not move camera you can avoid this by calling:
                // renderer.getMarker(clusterItem).showInfoWindow();
                false
            }
            map.setOnMarkerClickListener(it)

            it.markerCollection.setOnInfoWindowAdapter(VehicleInfoViewAdapter(LayoutInflater.from(context)))
            map.setInfoWindowAdapter(it.markerManager)
            it.setOnClusterItemInfoWindowClickListener { vehicleItem -> notifyShort("Item: ${vehicleItem.title} clicked!") }
            map.setOnInfoWindowClickListener(it)
        }

        // Get vehicles
        notifyShort(getString(R.string.map_fetch_vehicles))
        progress_vehicles.visible()
        viewModel.getVehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)
    }

    private fun onVehiclesFetched(vehicles: List<VehicleView>?) {
        progress_vehicles.gone()
        if (!vehicles.isNullOrEmpty()) {
            clusterManager?.clearItems()
            addMarkersToCluster(vehicles)
            clusterManager?.cluster()
        } else {
            notifyLong(getString(R.string.map_no_results))
        }
    }

    private fun addMarkersToCluster(vehicles: List<VehicleView>) {
        clusterManager?.addItems(vehicles.toMutableList())
    }

    private fun showError(failure: Failure?) {
        progress_vehicles.gone()
        when (failure) {
            is Failure.ServerError -> {
                Timber.tag(TAG).d("ServerError: ${failure.throwable?.message}")
            }
        }
    }
}
