package com.assignment.kotlingooglemaps

import android.app.Application
import com.assignment.kotlingooglemaps.core.di.*
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by danieh on 01/08/2019.
 */
class KotlinGoogleMapsApp  : Application() {

    companion object {
        var appContext: KotlinGoogleMapsApp? = null
        var BASE_URL = "https://fake-poi-api.mytaxi.com"
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule(BASE_URL))
            .dataModule(DataModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        this.injectMembers()
        this.initializeLeakDetection()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
