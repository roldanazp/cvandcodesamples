package com.roldansworkshop.cv

import android.app.Application
import com.udacity.shoestore.timber.CVTree
import timber.log.Timber
/**
 * Extends [Application] to setup global configurations
 */
class App: Application() {

    /**
     * Initialize [Timber]
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(CVTree())

    }
}