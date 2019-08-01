package com.assignment.kotlingooglemaps.core.di

import com.assignment.kotlingooglemaps.features.data.repository.VehiclesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by danieh on 01/08/2019.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideVehiclesRepository(dataSource: VehiclesRepository.Network): VehiclesRepository = dataSource
}
