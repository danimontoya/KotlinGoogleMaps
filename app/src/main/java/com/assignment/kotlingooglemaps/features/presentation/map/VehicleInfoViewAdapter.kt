package com.assignment.kotlingooglemaps.features.presentation.map

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.assignment.kotlingooglemaps.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

/**
 * Created by danieh on 02/08/2019.
 */
class VehicleInfoViewAdapter(private val layoutInflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

    @SuppressLint("InflateParams")
    override fun getInfoContents(marker: Marker?): View {
        val popup = layoutInflater.inflate(R.layout.info_window_layout, null)

        (popup.findViewById(R.id.vehicle_name) as TextView).text = marker?.snippet
//        val image = (popup.findViewById(R.id.place_image) as ImageView)
//        GlideApp.with(image.context).asGif().load(R.drawable.get_me_high).into(image)

        return popup
    }

    override fun getInfoWindow(marker: Marker?): View? {
//        val popup = layoutInflater.inflate(R.layout.info_window_layout, null)
//
//        (popup.findViewById(R.id.place_name) as TextView).setText(marker?.getSnippet())
//        val image = (popup.findViewById(R.id.place_image) as ImageView)
//        GlideApp.with(image.context)
//            .asGif()
//            .load(R.drawable.get_me_high)
//            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
//            .into(image)
//
//        return popup
        return null
    }
}
