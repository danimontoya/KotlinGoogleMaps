package com.assignment.kotlingooglemaps.features.domain.usecase

import com.assignment.kotlingooglemaps.core.interactor.UseCase
import com.assignment.kotlingooglemaps.features.data.repository.VehiclesRepository
import com.assignment.kotlingooglemaps.features.domain.model.Vehicle
import javax.inject.Inject

/**
 * Created by danieh on 02/08/2019.
 */
class GetVehiclesInBoundsUseCase @Inject constructor(private val vehiclesRepository: VehiclesRepository) :
        UseCase<List<Vehicle>, GetVehiclesInBoundsUseCase.Params>() {

    override suspend fun run(params: Params) = vehiclesRepository.vehiclesInBounds(params.p1Lat, params.p1Lon, params.p2Lat, params.p2Lon)

    data class Params(val p1Lat: Double, val p1Lon: Double, val p2Lat: Double, val p2Lon: Double)
}