package com.assignment.kotlingooglemaps.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by danieh on 01/08/2019.
 */
@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun context() = context
}
