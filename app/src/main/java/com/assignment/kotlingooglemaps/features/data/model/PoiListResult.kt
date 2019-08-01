package com.assignment.kotlingooglemaps.features.data.model

import com.assignment.kotlingooglemaps.features.domain.model.Vehicle

data class PoiListResult(
    val poiList: List<VehicleEntity>
)

data class VehicleEntity(
    val id: Int,
    val fleetType: String,
    val coordinate: Coordinate,
    val heading: Double
) {
    companion object {
        fun empty() = VehicleEntity(0, "", Coordinate(0.0, 0.0), 0.0)
    }

    fun toVehicle() = Vehicle(id, fleetType, coordinate.latitude, coordinate.longitude, heading)
}

data class Coordinate(
    val latitude: Double,
    val longitude: Double
)