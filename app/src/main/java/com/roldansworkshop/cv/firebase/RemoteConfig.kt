package com.roldansworkshop.cv.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

class RemoteConfig () {

    val splashscreenDelayKey = "splashscreen_delay"
    val splashscreenDelay: Long
        get() = Firebase.remoteConfig.getLong(splashscreenDelayKey)


}