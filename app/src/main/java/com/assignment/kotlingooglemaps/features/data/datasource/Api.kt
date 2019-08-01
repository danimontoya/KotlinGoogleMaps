package com.assignment.kotlingooglemaps.features.data.datasource

import com.assignment.kotlingooglemaps.features.data.model.PoiListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by danieh on 01/08/2019.
 */
interface Api {

    companion object {
        private const val P1_LAT = "p1Lat"
        private const val P1_LON = "p1Lon"
        private const val P2_LAT = "p2Lat"
        private const val P2_LON = "p2Lon"
    }

    @GET("/")
    fun vehiclesInBounds(@Query(P1_LAT) p1Lat: Double, @Query(P1_LON) p1Lon: Double, @Query(P2_LAT) p2Lat: Double, @Query(P2_LON) p2Lon: Double): Call<PoiListResult>
}