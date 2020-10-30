package com.otb.photosearch

import android.app.Application
import com.otb.photosearch.di.apiModule
import com.otb.photosearch.di.databaseModule
import com.otb.photosearch.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by Mohit Rajput on 27/9/20.
 */
class ImagesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ImagesApp)
            androidFileProperties()
            modules(provideModules())
        }
    }

    private fun provideModules() = listOf(retrofitModule, apiModule, databaseModule)
}