package com.assignment.kotlingooglemaps.features.data.model

import androidx.annotation.Keep
import com.assignment.kotlingooglemaps.features.domain.model.Vehicle
import com.google.gson.annotations.SerializedName

@Keep
data class PoiListResult(
    @SerializedName("poiList")
    val poiList: List<VehicleEntity>
)

@Keep
data class VehicleEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("fleetType")
    val fleetType: String,
    @SerializedName("coordinate")
    val coordinate: Coordinate,
    @SerializedName("heading")
    val heading: Double
) {
    fun toVehicle() = Vehicle(id, fleetType, coordinate.latitude, coordinate.longitude, heading)
}

@Keep
data class Coordinate(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)