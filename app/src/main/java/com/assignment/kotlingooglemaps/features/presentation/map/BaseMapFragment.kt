package com.assignment.kotlingooglemaps.features.presentation.map

import android.os.Bundle
import android.view.View
import com.assignment.kotlingooglemaps.R
import com.assignment.kotlingooglemaps.core.extension.gone
import com.assignment.kotlingooglemaps.core.extension.visible
import com.assignment.kotlingooglemaps.core.platform.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * Created by danieh on 02/08/2019.
 */
abstract class BaseMapFragment : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    abstract fun setupDone()

    companion object {
        const val P1_LAT = 53.694865
        const val P1_LON = 9.757589

        const val P2_LAT = 53.394655
        const val P2_LON = 10.099891

        val HAMBURG = LatLng(53.551086, 9.993682)

        const val VEHICLE_ZOOM = 16.0F
        const val CITY_ZOOM_FAR = 9.0F
    }

    lateinit var map: GoogleMap

    override fun layoutId() = R.layout.fragment_map

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        progress_vehicles.visible()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        progress_vehicles.gone()

        // Set all the settings of the map to match the current state of the checkboxes
        with(map.uiSettings) {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isIndoorLevelPickerEnabled = false
            isMapToolbarEnabled = true
            isZoomGesturesEnabled = true
            isScrollGesturesEnabled = true
            isTiltGesturesEnabled = true
            isRotateGesturesEnabled = true
        }

        map.setOnMarkerClickListener(this)

        // move the camera to Hamburg
        animateCamera(HAMBURG, CITY_ZOOM_FAR)

        map.apply {
            isBuildingsEnabled = true
        }
        setupDone()
    }

    internal fun animateCamera(target: LatLng, zoom: Float) {
        val position = CameraPosition.Builder()
            .target(target)
            .zoom(zoom)
            .bearing(0f)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(position))
    }

    override fun onMarkerClick(p0: Marker?) = false
}
