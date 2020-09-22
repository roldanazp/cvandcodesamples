package com.roldansworkshop.cv.module.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.roldansworkshop.cv.AbstractFragment
import com.roldansworkshop.cv.App
import com.roldansworkshop.cv.databinding.FragmentHomeBinding
import com.roldansworkshop.cv.model.Profile
import com.roldansworkshop.cv.module.home.presenter.BulletPointsPresenter
import com.roldansworkshop.cv.module.home.presenter.ContactPresenter
import com.roldansworkshop.cv.module.home.presenter.ElevatorPitchPresenter
import com.roldansworkshop.cv.module.home.presenter.ExperiencePresenter
import com.roldansworkshop.cv.module.splashscreen.SplashScreenFragment
import com.roldansworkshop.cv.viewmodel.MainViewModel
import javax.inject.Inject

/**
 * This fragment show user four screens animated by drag events using MotionLayout
 */
class HomeFragment : AbstractFragment(){

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    @Inject lateinit var contactPresenter:ContactPresenter
    @Inject lateinit var bulletPointsPresenter:BulletPointsPresenter
    @Inject lateinit var experiencePresenter:ExperiencePresenter
    @Inject lateinit var elevatorPitchPresenter:ElevatorPitchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context?.applicationContext as App).navController =  findNavController()
        (context?.applicationContext as App).appComponent?.inject(this)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        observeProfile()
        return binding.root
    }

    fun observeProfile(){
        mainViewModel.profileLiveData.observe(viewLifecycleOwner, Observer<Profile>{ profile ->

            contactPresenter.profile = profile
            elevatorPitchPresenter.profile = profile

            binding.iQuadrant1.presenter = elevatorPitchPresenter
            binding.iQuadrant2.presenter = experiencePresenter
            binding.iQuadrant3.presenter = contactPresenter
            binding.iQuadrant4.presenter = bulletPointsPresenter
            Glide.with(this).load(profile.image).into(binding.iQuadrant1.ivProfileImage)
            /**
             * TESTING
             * release testing idling resource
             */
            this.decrement(SplashScreenFragment::class.qualifiedName)
        })
    }

}