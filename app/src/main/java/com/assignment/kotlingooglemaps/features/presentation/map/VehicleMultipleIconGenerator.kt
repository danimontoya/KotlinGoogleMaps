package com.assignment.kotlingooglemaps.features.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.assignment.kotlingooglemaps.R
import com.assignment.kotlingooglemaps.core.extension.gone
import com.assignment.kotlingooglemaps.core.extension.visible

/**
 * Created by danieh on 02/08/2019.
 */
class VehicleMultipleIconGenerator(context: Context?) {

    @SuppressLint("InflateParams")
    private val container: ViewGroup = LayoutInflater.from(context).inflate(R.layout.marker_cluster, null) as ViewGroup
    private val image: ImageView
    private val text: TextView

    init {
        image = container.findViewById(R.id.marker)
        text = container.findViewById(R.id.marker_count)
    }

    fun makeIcon(count: Int?): Bitmap {

        if (count != null && count > 0) {
            text.visible()
            text.text = count.toString()
        } else {
            text.gone()
        }

        val measuredWidth = container.measuredWidth
        val measuredHeight = container.measuredHeight
        val measureSpec = if (measuredHeight > 0) {
            View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        container.measure(measureSpec, measureSpec)
        container.layout(0, 0, measuredWidth, measuredHeight)

        val r = Bitmap.createBitmap(container.measuredWidth, container.measuredHeight, Bitmap.Config.ARGB_8888)
        r.eraseColor(Color.TRANSPARENT)

        val canvas = Canvas(r)
        container.draw(canvas)
        return r
    }
}
