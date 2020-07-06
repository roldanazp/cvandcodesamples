package com.roldansworkshop.cv.module.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.model.Profile
import com.roldansworkshop.cv.viewmodel.MainViewModel
import java.util.*
import kotlin.concurrent.schedule
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.roldansworkshop.cv.AbstractFragment


const val SPLASHSCREEN_DELAY_NAME = "splashscreen_delay_name"
const val SPLASHSCREEN_DELAY = 1500L

class SplashScreenFragment : AbstractFragment() {

    private var splashScreenDelayelapsed = false
    private val shoeViewModel: MainViewModel by activityViewModels()

    /**
     * Fordward delay [TimerTask] for the animation to shine
     */
    private lateinit var timerTask: TimerTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * TESTING
         * Lock testing idling resource
         */
        this.increment(SplashScreenFragment::class.qualifiedName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        shoeViewModel.profileLiveData.observe(viewLifecycleOwner, Observer<Profile>{ profile ->
            if(splashScreenDelayelapsed){
                forward()
            }
        })
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    /**
     * Go to the destination [SplashScreenFragment.getNavitationAction] returns
     */
    private fun forward() {
        val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    /**
     * Re-start [SplashScreenFragment.timerTask] count down, time resets as well
     */
    override fun onResume() {
        super.onResume()
        startCountDown()
    }

    /**
     * Stop [SplashScreenFragment.timerTask], it wold be confusing for the user switching views if
     * app its on the background
     */
    override fun onPause() {
        super.onPause()
        timerTask.cancel()
    }

    /**
     * Start [SplashScreenFragment.timerTask] count down of [SPLASHSCREEN_DELAY]
     */
    private fun startCountDown() {
        timerTask = Timer(SPLASHSCREEN_DELAY_NAME, false)
            .schedule(SPLASHSCREEN_DELAY) {
                splashScreenDelayelapsed = true

                shoeViewModel.profileLiveData.value?.let {
                    forward()
                }

            }
    }

}