package com.roldansworkshop.cv.dagger
import com.roldansworkshop.cv.module.home.HomeFragment
import dagger.Component

@Component(modules = [PresenterModule::class])
interface ApplicationComponent {
    fun inject(fragment: HomeFragment)
}