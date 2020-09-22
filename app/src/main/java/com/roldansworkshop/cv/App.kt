package com.roldansworkshop.cv

import android.app.Application
import androidx.navigation.NavController
import com.roldansworkshop.cv.dagger.ApplicationComponent
import com.roldansworkshop.cv.dagger.DaggerApplicationComponent
import com.roldansworkshop.cv.dagger.PresenterModule
import com.udacity.shoestore.timber.CVTree
import timber.log.Timber
/**
 * Extends [Application] to setup global configurations
 */
class App: Application() {

    var appComponent: ApplicationComponent? = null
    var navController: NavController? = null

    /**
     * Initialize [Timber]
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(CVTree())
        appComponent = DaggerApplicationComponent.builder().presenterModule(PresenterModule(this)).build()
    }

}