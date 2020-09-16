package com.udacity.shoestore.timber

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CVTree: Timber.Tree() {
    private val firebaseCrashlytics = FirebaseCrashlytics.getInstance()
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        when(priority){
            Log.VERBOSE, Log.DEBUG,  Log.INFO, Log.ASSERT -> {
                Log.println(priority, tag, message)
            }
            Log.WARN, Log.ERROR -> {
                throwable
                    ?.let{ firebaseCrashlytics.recordException(it) }
                    ?:run{ firebaseCrashlytics.log("$tag, $message")}
            }
        }
    }
}
