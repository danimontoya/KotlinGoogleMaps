package com.assignment.kotlingooglemaps.features.data.repository

import arrow.core.Either
import com.assignment.kotlingooglemaps.core.exception.Failure
import com.assignment.kotlingooglemaps.core.platform.NetworkHandler
import com.assignment.kotlingooglemaps.features.data.datasource.VehiclesService
import com.assignment.kotlingooglemaps.features.domain.model.Vehicle
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by danieh on 01/08/2019.
 */
interface VehiclesRepository {

    fun vehiclesInBounds(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double): Either<Failure, List<Vehicle>>

    class Network @Inject constructor(private val networkHandler: NetworkHandler, private val service: VehiclesService) : VehiclesRepository {

        override fun vehiclesInBounds(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double): Either<Failure, List<Vehicle>> {
            return when (networkHandler.isConnected) {
                true -> request(service.vehiclesInBounds(p1Lat, p1Lon, p2Lat, p2Lon)) { poiListResult -> poiListResult.poiList.map { it.toVehicle() } }
                false, null -> Either.Left(Failure.NetworkConnection())
            }
        }

        private fun <T, R> request(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> response.body()?.let { Either.Right(transform(it)) } ?: Either.Left(Failure.BodyNullError())
                    false -> Either.Left(Failure.ServerError(Throwable(response.errorBody()?.string())))
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError(exception))
            }
        }
    }
}