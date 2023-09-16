package it.fantacalcio.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FantacalcioSampleApplication : Application() {

    private val TAG = FantacalcioSampleApplication::class.java

    companion object {

        lateinit var instance : FantacalcioSampleApplication
            private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}