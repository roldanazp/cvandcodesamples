package com.roldansworkshop.cv.module.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.roldansworkshop.cv.viewmodel.MainViewModel
import java.util.*
import kotlin.concurrent.schedule
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.roldansworkshop.cv.AbstractFragment
import com.roldansworkshop.cv.firebase.RemoteConfig
import com.roldansworkshop.cv.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : AbstractFragment() {

    private var isSplashScreenDelayElapsed = false
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
        shoeViewModel.profileLiveData.observe(viewLifecycleOwner, Observer{ forward() })
        shoeViewModel.remoteConfig.observe(viewLifecycleOwner, Observer{ remoteConfig -> startCountDown(remoteConfig) })
        return FragmentSplashScreenBinding.inflate(inflater, container, false).root
    }

    /**
     * Go to the destination [SplashScreenFragment.getNavitationAction] returns
     */
    private fun forward() {
        if(isSplashScreenDelayElapsed && shoeViewModel.profileLiveData.value != null
            && shoeViewModel.remoteConfig.value != null){
            val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()
            findNavController().navigate(action)
        }
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
     * Start [SplashScreenFragment.timerTask] count down of remote configs
     */
    private fun startCountDown(remoteConfig: RemoteConfig) {
        timerTask = Timer(remoteConfig.splashscreenDelayKey, false)
            .schedule(remoteConfig.splashscreenDelay) {
                isSplashScreenDelayElapsed = true
                forward()
            }
    }
}