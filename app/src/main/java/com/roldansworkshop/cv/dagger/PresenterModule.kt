package com.roldansworkshop.cv.dagger

import android.content.Intent
import android.net.Uri
import com.roldansworkshop.cv.App
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.model.Profile
import com.roldansworkshop.cv.module.home.HomeFragmentDirections
import com.roldansworkshop.cv.module.home.presenter.BulletPointsPresenter
import com.roldansworkshop.cv.module.home.presenter.ContactPresenter
import com.roldansworkshop.cv.module.home.presenter.ElevatorPitchPresenter
import com.roldansworkshop.cv.module.home.presenter.ExperiencePresenter
import dagger.Module
import dagger.Provides
import java.lang.Exception

@Module
class PresenterModule(val app: App) {

    @Provides
    fun provideContactPresenter(): ContactPresenter = object : ContactPresenter{

            override var profile: Profile? = null

            override fun onPhoneNumberSelected() {
                profile?.phone?.let {
                    try{
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse(app.getString(R.string.phone_number_uri, it.countryCode, it.number))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        app.startActivity(intent)
                    }catch(exception: Exception){

                    }
                }
            }

            override fun onEmailSelected() {
                profile?.email?.let {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(it))
                    intent.type = app.getString(R.string.email_message_code)
                    val chooserIntent = Intent.createChooser(intent, app.getString(R.string.email_selector_title))
                    chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    app.startActivity(chooserIntent)
                }
            }

            override fun onLocationSelected() {
                profile?.address?.let {
                    val geoLocation = app.getString(R.string.geo_location_template,it.location.latitude, it.location.longitude)
                    val gmmIntentUri = Uri.parse(geoLocation)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage(app.getString(R.string.google_maps_package))
                    mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    app.packageManager?.let{mapIntent.resolveActivity(it)?.let {
                        app.startActivity(mapIntent)
                    }}
                }
            }

        }

    @Provides
    fun provideBulletPointsPresenter(): BulletPointsPresenter = object : BulletPointsPresenter{
        override fun onBulletPointsSelected() {
            val action = HomeFragmentDirections.actionHomeFragmentToBulletPointsFragment()
            app.navController?.navigate(action)
            //TODO why i increment here but not on the othe*/
        }
    }

    @Provides
    fun provideExperiencePresenter(): ExperiencePresenter = object : ExperiencePresenter{
        override fun onWorkExperienceSelected() {
            val action = HomeFragmentDirections.actionHomeFragmentToProjectsFragment()
            app.navController?.navigate(action)
        }

        override fun onExamplesSelected() {
            val action = HomeFragmentDirections.actionHomeFragmentToSamplesFragment()
            app.navController?.navigate(action)
        }
    }
    @Provides
    fun provideElevatorPitchPresenter(): ElevatorPitchPresenter = object : ElevatorPitchPresenter{
        override var profile: Profile? = null
    }
}