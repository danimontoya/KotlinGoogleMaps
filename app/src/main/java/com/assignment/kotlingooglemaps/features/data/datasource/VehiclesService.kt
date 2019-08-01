package com.assignment.kotlingooglemaps.features.data.datasource

import com.assignment.kotlingooglemaps.features.data.model.PoiListResult
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by danieh on 01/08/2019.
 */
@Singleton
class VehiclesService @Inject constructor(retrofit: Retrofit) : Api {

    private val api by lazy { retrofit.create(Api::class.java) }

    override fun vehiclesInBounds(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double): Call<PoiListResult> =
        api.vehiclesInBounds(p1Lat, p1Lon, p2Lat, p2Lon)
}
