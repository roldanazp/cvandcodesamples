package com.roldansworkshop.cv

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.*

/**
 * Add idling resource variable and methods to facilitate async testing
 */
abstract class AbstractActivity : AppCompatActivity() {

    private val countingIdlingResource: CountingIdlingResource? =
        CountingIdlingResource("picky")
    private val changes: MutableMap<String, Boolean> =
        HashMap()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        FirebaseAnalytics.getInstance(this).logEvent(toString(), null)
    }

    @get:VisibleForTesting
    val idlingResource: IdlingResource?
        get() = countingIdlingResource

    fun increment(id: String) {
        if (countingIdlingResource != null) {
            changes[id] = true
            countingIdlingResource.increment()
        }
    }

    fun decrement(id: String) {
        if (countingIdlingResource != null && changes.containsKey(id) && changes[id]!!) {
            changes[id] = false
            countingIdlingResource.decrement()
        }
    }

}