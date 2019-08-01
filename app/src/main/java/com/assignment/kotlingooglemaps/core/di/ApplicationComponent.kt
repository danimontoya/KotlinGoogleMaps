package com.assignment.kotlingooglemaps.core.di

import com.assignment.kotlingooglemaps.KotlinGoogleMapsApp
import com.assignment.kotlingooglemaps.core.di.viewmodel.ViewModelModule
import com.assignment.kotlingooglemaps.features.presentation.MainActivity
import com.assignment.kotlingooglemaps.features.presentation.map.MapFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by danieh on 01/08/2019.
 */
@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(application: KotlinGoogleMapsApp)

    fun inject(mainActivity: MainActivity)

    fun inject(mapFragment: MapFragment)
}
