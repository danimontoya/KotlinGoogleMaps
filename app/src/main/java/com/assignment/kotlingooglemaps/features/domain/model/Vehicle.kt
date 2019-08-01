package com.assignment.kotlingooglemaps.features.domain.model

import com.assignment.kotlingooglemaps.features.presentation.model.VehicleView

/**
 * Created by danieh on 01/08/2019.
 */
data class Vehicle(
    private val id: Int,
    private val fleetType: String,
    private val latitude: Double,
    private val longitude: Double,
    private val heading: Double
) {

    fun toVehicleView() = VehicleView(id, fleetType, latitude, longitude, heading)

    companion object {
        fun empty() = Vehicle(0, "", 0.0, 0.0, 0.0)
    }
}