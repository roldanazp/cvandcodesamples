package com.roldansworkshop.cv.module.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.roldansworkshop.cv.AbstractFragment
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.databinding.FragmentHomeBinding
import com.roldansworkshop.cv.model.Profile
import com.roldansworkshop.cv.module.bulletpoints.BulletPointsFragment
import com.roldansworkshop.cv.module.home.presenter.BulletPointsPresenter
import com.roldansworkshop.cv.module.home.presenter.ContactPresenter
import com.roldansworkshop.cv.module.home.presenter.ElevatorPitchPresenter
import com.roldansworkshop.cv.module.home.presenter.ExperiencePresenter
import com.roldansworkshop.cv.module.splashscreen.SplashScreenFragment
import com.roldansworkshop.cv.viewmodel.MainViewModel
import com.roldansworkshop.cv.module.experience.work.ProjectsFragment
import java.lang.Exception

/**
 * This fragment show user four screens animated by drag events using MotionLayout
 */
class HomeFragment : AbstractFragment(),
    ContactPresenter,
    BulletPointsPresenter,
    ExperiencePresenter,
    ElevatorPitchPresenter {

    private val mainViewModel: MainViewModel by activityViewModels()
    override var profile: Profile? = null
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        observeProfile()
        return binding.root
    }

    fun observeProfile(){
        mainViewModel.profileLiveData.observe(viewLifecycleOwner, Observer<Profile>{ profile ->
            this@HomeFragment.profile = profile
            binding.iQuadrant1.presenter = this
            binding.iQuadrant2.presenter = this
            binding.iQuadrant3.presenter = this
            binding.iQuadrant4.presenter = this
            Glide.with(this).load(profile.image).into(binding.iQuadrant1.ivProfileImage);
            /**
             * TESTING
             * release testing idling resource
             */
            this.decrement(SplashScreenFragment::class.qualifiedName)
        })
    }

    /**
     * Opens calls manager ready to call me hahaha
     */
    override fun onPhoneNumberSelected() {
        profile?.phone?.let {
            try{
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(getString(R.string.phone_number_uri, it.countryCode, it.number))
                startActivity(intent)
            }catch(exception:Exception){

            }
        }
    }

    /**
     * Opens email app picker passing my email
     */
    override fun onEmailSelected() {
        profile?.email?.let {
            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf(it))
            email.type = getString(R.string.email_message_code)
            startActivity(Intent.createChooser(email, getString(R.string.email_selector_title)))
        }

    }

    /**
     * Opens maps around where I live
     */
    override fun onLocationSelected() {
        profile?.address?.let {
            val geoLocation = getString(R.string.geo_location_template,it.location.latitude, it.location.longitude)
            val gmmIntentUri = Uri.parse(geoLocation)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage(getString(R.string.google_maps_package))
            activity?.packageManager?.let{mapIntent.resolveActivity(it)?.let {
                startActivity(mapIntent)
            }}
        }

    }

    /**
     * Display [BulletPointsFragment]
     */
    override fun onBulletPointsSelected() {
        val action = HomeFragmentDirections.actionHomeFragmentToBulletPointsFragment()
        findNavController().navigate(action)
        //TODO why i increment here but not on the othe
        this.increment(BulletPointsFragment::class.qualifiedName)
    }

    /**
     * Display [ProjectsFragment]
     */
    override fun onWorkExperienceSelected() {
        val action = HomeFragmentDirections.actionHomeFragmentToProjectsFragment()
        findNavController().navigate(action)
    }

    /**
     * Display [ExamplesFragment]
     */
    override fun onExamplesSelected() {
        val action = HomeFragmentDirections.actionHomeFragmentToSamplesFragment()
        findNavController().navigate(action)
    }

}