package com.assignment.kotlingooglemaps.features.presentation.map

import android.os.Bundle
import android.view.View
import com.assignment.kotlingooglemaps.R
import com.assignment.kotlingooglemaps.core.exception.Failure
import com.assignment.kotlingooglemaps.core.extension.failure
import com.assignment.kotlingooglemaps.core.extension.gone
import com.assignment.kotlingooglemaps.core.extension.observe
import com.assignment.kotlingooglemaps.core.extension.viewModel
import com.assignment.kotlingooglemaps.core.platform.BaseFragment
import com.assignment.kotlingooglemaps.features.presentation.model.VehicleView
import kotlinx.android.synthetic.main.fragment_map.*
import timber.log.Timber

/**
 * Created by danieh on 01/08/2019.
 */
class MapFragment : BaseFragment() {

    companion object {
        private val TAG = MapFragment::class.java.simpleName

        private const val p1Lat = 53.694865
        private const val p1Lon = 9.757589
        private const val p2Lat = 53.394655
        private const val p2Lon = 10.099891
    }

    override fun layoutId() = R.layout.fragment_map

    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setHasOptionsMenu(true)

        viewModel = viewModel(viewModelFactory) {
            observe(vehicleList, ::onVehiclesFetched)
            failure(failure, ::showError)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getVehiclesInBounds(p1Lat, p1Lon, p2Lat, p2Lon)
    }

    private fun onVehiclesFetched(vehicles: List<VehicleView>?) {
        progress_vehicles.gone()
        if (vehicles != null && vehicles.isNotEmpty()) {
            vehicles_text.text = vehicles.toString()

        } else {
            notify(getString(R.string.map_no_results))
        }
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