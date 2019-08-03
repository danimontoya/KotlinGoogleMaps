package com.assignment.kotlingooglemaps.features.presentation.map

import android.content.Context
import com.assignment.kotlingooglemaps.features.presentation.model.VehicleView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

/**
 * Created by danieh on 02/08/2019.
 */
class VehicleClusterRenderer(
        context: Context?, map: GoogleMap,
        clusterManager: ClusterManager<VehicleView>
) : DefaultClusterRenderer<VehicleView>(context, map, clusterManager) {

    private val vehicleSingleIconGenerator = VehicleSingleIconGenerator(context?.applicationContext)

    private val vehicleMultipleIconGenerator = VehicleMultipleIconGenerator(context?.applicationContext)

    override fun onBeforeClusterItemRendered(item: VehicleView?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        val icon = vehicleSingleIconGenerator.makeIcon(item?.fleetType, item?.heading)
        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon))?.snippet(item?.title)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<VehicleView>?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterRendered(cluster, markerOptions)

        val icon = vehicleMultipleIconGenerator.makeIcon(cluster?.size)
        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }
}