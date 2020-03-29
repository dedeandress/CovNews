package com.dedeandres.covnews

import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.blongho.country_data.World
import com.dedeandres.covnews.di.covNewsApp
import com.dedeandres.covnews.di.networkDataSourceModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import java.util.*

class App : LocalizationApplication(){

    override fun onCreate() {
        super.onCreate()


        startKoin(this, covNewsApp + networkDataSourceModule)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        World.init(this)

    }

    override fun getDefaultLanguage(): Locale {
        return Locale.ENGLISH
    }

}