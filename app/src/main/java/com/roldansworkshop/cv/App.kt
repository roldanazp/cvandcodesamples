package com.roldansworkshop.cv

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
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
        initRemoteConfig()
    }

    private fun initRemoteConfig(){

    }

}