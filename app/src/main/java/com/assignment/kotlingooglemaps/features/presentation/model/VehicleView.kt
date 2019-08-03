package com.assignment.kotlingooglemaps.features.presentation.model

import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * Created by danieh on 01/08/2019.
 */
@Keep
data class VehicleView(
        val id: Int,
        val fleetType: String,
        val latLng: LatLng,
        val heading: Double
) : ClusterItem {

    override fun getSnippet() = fleetType

    override fun getTitle() = fleetType

    override fun getPosition() = latLng

}
