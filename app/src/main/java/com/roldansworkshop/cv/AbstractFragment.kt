package com.roldansworkshop.cv

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Add idling resource methods to facilitate async testing
 */
abstract class AbstractFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { FirebaseAnalytics.getInstance(it).logEvent(toString(), null) }
    }

    fun increment(id: String?) {
        if (activity != null && activity is AbstractActivity) {
            (activity as AbstractActivity?)!!.increment(id!!)
        }
    }

    fun decrement(id: String?) {
        if (activity != null && activity is AbstractActivity) {
            (activity as AbstractActivity?)!!.decrement(id!!)
        }
    }
}