package chistousov.ilya.passwordkeeper

import android.app.Application
import chistousov.ilya.common.Core
import chistousov.ilya.common.CoreProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var coreProvider: CoreProvider
    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
    }
}