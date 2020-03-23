package com.dedeandres.covnews

import android.app.Application
import com.blongho.country_data.World
import com.dedeandres.covnews.di.covNewsApp
import com.dedeandres.covnews.di.networkDataSourceModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin(this, covNewsApp + networkDataSourceModule)
        Timber.plant(Timber.DebugTree())

        World.init(this)

    }

}