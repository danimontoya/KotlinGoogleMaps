package com.assignment.kotlingooglemaps.features.data.repository

import arrow.core.Either
import com.assignment.kotlingooglemaps.UnitTest
import com.assignment.kotlingooglemaps.core.exception.Failure
import com.assignment.kotlingooglemaps.core.platform.NetworkHandler
import com.assignment.kotlingooglemaps.features.data.datasource.VehiclesService
import com.assignment.kotlingooglemaps.features.data.model.Coordinate
import com.assignment.kotlingooglemaps.features.data.model.PoiListResult
import com.assignment.kotlingooglemaps.features.data.model.VehicleEntity
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

/**
 * Created by danieh on 03/08/2019.
 */
class VehiclesRepositoryTest : UnitTest() {

    companion object {
        const val P1_LAT = 53.694865
        const val P1_LON = 9.757589

        const val P2_LAT = 53.394655
        const val P2_LON = 10.099891
    }

    private lateinit var repository: VehiclesRepository.Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: VehiclesService

    @Mock
    private lateinit var call: Call<PoiListResult>
    @Mock
    private lateinit var response: Response<PoiListResult>

    @Before
    fun setUp() {
        repository = VehiclesRepository.Network(networkHandler, service)
    }

    @Test
    fun `poi list call should return a failure if body is null`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { response.body() }.willReturn(null)
        given { response.isSuccessful }.willReturn(true)
        given { call.execute() }.willReturn(response)
        given { service.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON) }.willReturn(call)

        val result = repository.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)

        result.isLeft() shouldEqual true
        result.fold({ failure -> failure shouldBeInstanceOf Failure.BodyNullError::class.java }, {})
        verify(service).vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)
    }

    @Test
    fun `poi list call should return the list of vehicles`() {

        val entityList = listOf(
            VehicleEntity(328583, "TAXI", Coordinate(53.63234382302951, 9.864085363305973), 107.26297337826972),
            VehicleEntity(507124, "POOLING", Coordinate(53.58585998785532, 10.051962794592836), 76.09989051210395)
        )
        val poiListResult = PoiListResult(entityList)
        val vehiclesResult = poiListResult.poiList.map { it.toVehicle() }

        given { networkHandler.isConnected }.willReturn(true)
        given { response.body() }.willReturn(poiListResult)
        given { response.isSuccessful }.willReturn(true)
        given { call.execute() }.willReturn(response)
        given { service.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON) }.willReturn(call)

        val result = repository.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)

        result shouldEqual Either.Right(vehiclesResult)
        verify(service).vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)
    }

    @Test
    fun `vehicles service should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = repository.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)

        result shouldBeInstanceOf Either::class.java
        result.isLeft() shouldEqual true
        result.fold({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `vehicles service should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val result = repository.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)

        result shouldBeInstanceOf Either::class.java
        result.isLeft() shouldEqual true
        result.fold({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test
    fun `vehicles service should return server error if no successful response`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { response.isSuccessful }.willReturn(false)
        given { call.execute() }.willReturn(response)
        given { service.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON) }.willReturn(call)

        val result = repository.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)

        result shouldBeInstanceOf Either::class.java
        result.isLeft() shouldEqual true
        result.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }

    @Test
    fun `vehicles call should catch exceptions`() {
        given { networkHandler.isConnected }.willReturn(true)

        val result = repository.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)

        result shouldBeInstanceOf Either::class.java
        result.isLeft() shouldEqual true
        result.fold({ failure -> failure shouldBeInstanceOf Failure.ServerError::class.java }, {})
    }
}
