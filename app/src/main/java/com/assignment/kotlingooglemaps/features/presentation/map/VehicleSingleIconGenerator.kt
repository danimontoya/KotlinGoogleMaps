package com.assignment.kotlingooglemaps.features.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.LayoutInflater
import android.view.View
import com.assignment.kotlingooglemaps.R

/**
 * Created by danieh on 02/08/2019.
 */
class VehicleSingleIconGenerator(val context: Context?) {

    companion object {
        private const val FLEET_TYPE_TAXI = "TAXI"
    }

    @SuppressLint("InflateParams")
    private val container = LayoutInflater.from(context).inflate(R.layout.marker_single, null)

    fun makeIcon(fleetType: String?, heading: Double?): Bitmap {
        val measuredWidth = container.measuredWidth
        val measuredHeight = container.measuredHeight
        val measureSpec = if (measuredHeight > 0) {
            View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }

        container.measure(measureSpec, measureSpec)
        container.layout(0, 0, measuredWidth, measuredHeight)

        val matrix = Matrix()
        matrix.postRotate(heading?.toFloat() ?: 0f)

        val source = fleetType?.let {
            if (it == FLEET_TYPE_TAXI) {
                BitmapFactory.decodeResource(context?.resources, R.mipmap.ic_marker_taxi)
            } else {
                BitmapFactory.decodeResource(context?.resources, R.mipmap.ic_marker_pooling)
            }
        } ?: BitmapFactory.decodeResource(context?.resources, R.mipmap.ic_marker_taxi)
        val bitmap = Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
        //container.setImageBitmap(r)

        val canvas = Canvas(bitmap)
        container.draw(canvas)
        return bitmap
    }
}
