package com.assignment.kotlingooglemaps.features.presentation.map

import androidx.lifecycle.MutableLiveData
import com.assignment.kotlingooglemaps.core.platform.BaseViewModel
import com.assignment.kotlingooglemaps.features.domain.model.Vehicle
import com.assignment.kotlingooglemaps.features.domain.usecase.GetVehiclesInBoundsUseCase
import com.assignment.kotlingooglemaps.features.presentation.model.VehicleView
import javax.inject.Inject

/**
 * Created by danieh on 02/08/2019.
 */
class MapViewModel @Inject constructor(private val getVehiclesInBoundsUseCase: GetVehiclesInBoundsUseCase) : BaseViewModel() {

    var vehicleList: MutableLiveData<List<VehicleView>> = MutableLiveData()

    fun getVehiclesInBounds(p1Lat: Double, p1Lon: Double, p2Lat: Double, p2Lon: Double) =
            getVehiclesInBoundsUseCase(GetVehiclesInBoundsUseCase.Params(p1Lat, p1Lon, p2Lat, p2Lon)) {
                it.fold(::handleFailure, ::handleVehiclesInBounds)
            }

    private fun handleVehiclesInBounds(vehicles: List<Vehicle>) {
        vehicleList.value = vehicles.map { it.toVehicleView() }
    }
}
