package com.assignment.kotlingooglemaps.features.domain.usecase

import arrow.core.Either
import com.assignment.kotlingooglemaps.UnitTest
import com.assignment.kotlingooglemaps.core.exception.Failure
import com.assignment.kotlingooglemaps.features.data.repository.VehiclesRepository
import com.assignment.kotlingooglemaps.features.domain.model.Vehicle
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by danieh on 03/08/2019.
 */
class GetVehiclesInBoundsUseCaseTest : UnitTest() {

    companion object {
        const val P1_LAT = 53.694865
        const val P1_LON = 9.757589

        const val P2_LAT = 53.394655
        const val P2_LON = 10.099891
    }

    private lateinit var useCase: GetVehiclesInBoundsUseCase

    @Mock
    private lateinit var repository: VehiclesRepository

    @Before
    fun setUp() {
        useCase = GetVehiclesInBoundsUseCase(repository)
    }

    @Test
    fun `should get data from repository and return the list of vehicles within given bounds`() {
        val vehicles = listOf(
            Vehicle(328583, "TAXI", 53.63234382302951, 9.864085363305973, 107.26297337826972),
            Vehicle(507124, "POOLING", 53.58585998785532, 10.051962794592836, 76.09989051210395)
        )
        given { repository.vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON) }.willReturn(Either.Right(vehicles))

        var result: Either<Failure, List<Vehicle>>? = null
        runBlocking {
            result = useCase.run(GetVehiclesInBoundsUseCase.Params(P1_LAT, P1_LON, P2_LAT, P2_LON))
            result
        }

        verify(repository).vehiclesInBounds(P1_LAT, P1_LON, P2_LAT, P2_LON)
        result shouldEqual Either.Right(vehicles)
        verifyNoMoreInteractions(repository)
    }
}
