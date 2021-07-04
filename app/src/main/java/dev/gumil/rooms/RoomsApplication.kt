package dev.gumil.rooms

import android.app.Application
import dev.gumil.rooms.di.AppContainer
import dev.gumil.rooms.di.RoomsAppContainer
import timber.log.Timber

class RoomsApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        container = RoomsAppContainer(this)
    }
}
