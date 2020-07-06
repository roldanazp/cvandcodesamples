package com.roldansworkshop.cv

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.roldansworkshop.cv.databinding.ActivityMainBinding

/**
 * Covers [R.navigation.nav_graph]
 * Fragments:
 *  [com.roldansworkshop.cv.module.splashscreen.SplashScreenFragment]
 *  [com.roldansworkshop.cv.module.home.HomeFragment]
 *  [com.roldansworkshop.cv.module.bulletpoints.BulletPointsFragment]
 *  [com.roldansworkshop.cv.module.experience.work.ProjectsFragment]
 */
class MainActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}