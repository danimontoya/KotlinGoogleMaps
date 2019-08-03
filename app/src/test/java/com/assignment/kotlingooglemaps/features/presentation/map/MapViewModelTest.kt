package com.assignment.kotlingooglemaps.features.presentation.map

import arrow.core.Either
import com.assignment.kotlingooglemaps.AndroidTest
import com.assignment.kotlingooglemaps.features.domain.model.Vehicle
import com.assignment.kotlingooglemaps.features.domain.usecase.GetVehiclesInBoundsUseCase
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockito_kotlin.any
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.notification.Failure
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

/**
 * Created by danieh on 03/08/2019.
 */
class MapViewModelTest : AndroidTest() {

    companion object {
        const val P1_LAT = 53.694865
        const val P1_LON = 9.757589

        const val P2_LAT = 53.394655
        const val P2_LON = 10.099891
    }

    @Mock
    lateinit var useCase: GetVehiclesInBoundsUseCase

    private lateinit var viewModel: MapViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MapViewModel(useCase)
    }

    @Test
    fun `fetch vehicles within given bounds,result should be a list of vehicles`() {

        // Given or Arrange
        val vehicles = listOf(
            Vehicle(328583, "TAXI", 53.63234382302951, 9.864085363305973, 107.26297337826972),
            Vehicle(507124, "POOLING", 53.58585998785532, 10.051962794592836, 76.09989051210395)
        )
        val vehiclesResult = vehicles.map { it.toVehicleView() }
        Mockito.`when`(useCase(any(), any())).thenAnswer { answer ->
            answer.getArgument<(Either<Failure, List<Vehicle>>) -> Unit>(1)(Either.Right(vehicles))
        }

        // Then or Assert
        viewModel.vehicleList.observeForever {
            assertEquals(it!!.size, 2)
            assertEquals(it[0].id, 328583)
            assertEquals(it[0].fleetType, "TAXI")
            assertEquals(it[0].latLng, LatLng(53.63234382302951, 9.864085363305973))
            assertEquals(it[0].heading, 107.26297337826972)
            assertEquals(it[1].id, 507124)
            assertEquals(it[1].fleetType, "POOLING")
            it shouldEqual vehiclesResult
        }

        // When or Act
        runBlocking { viewModel.getVehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON) }
    }

}