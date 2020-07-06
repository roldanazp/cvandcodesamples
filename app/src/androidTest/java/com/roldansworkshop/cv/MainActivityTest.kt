package com.roldansworkshop.cv


import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.roldansworkshop.cv.viewmodel.MainViewModel
import org.junit.*
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private var idlingResource: IdlingResource? = null
    private var navController: NavController? = null
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun before() {
        idlingResource = activityTestRule.activity.idlingResource
        navController = activityTestRule.activity.findNavController(R.id.nav_host_fragment)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun after() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource)
        }
    }

    @Test
    fun mainActivityTest() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val mainViewModel =  ViewModelProviders.of(activityTestRule.activity).get(MainViewModel::class.java)
        Log.e("--->", "0")
        /**
         * Check for [com.roldansworkshop.cv.module.home.HomeFragment]
         */
        Log.e("checkpoint", "1")
        onView(withId(R.id.iv_profile_image)).check(matches(isDisplayed()))

        /**
         * Check for a valid profile
         */
        Log.e("--->", "2")
        val profile = mainViewModel.profileLiveData.value
        assert(profile!=null)

        /**
         * Move to bullet points quadrant
         */
        Log.e("--->", "3")
        var homeContainer:MotionLayout? = activityTestRule.activity.findViewById(R.id.ml_home_container)
        homeContainer?.transitionToState(R.id.cs_home_bottom_right)
        /**
         * Go to [com.roldansworkshop.cv.module.bulletpoints.BulletPointsFragment]
         */
        Log.e("--->", "4")
        onView(withId(R.id.b_bullet_points)).perform(click())

        /**
         * Check for [com.roldansworkshop.cv.module.bulletpoints.BulletPointsFragment] and
         * validate data
         */
        Log.e("--->", "5")
        onView(withId(R.id.tv_main_title)).check(matches(withText(appContext.getString(R.string.bullet_points_label))))
        val bulletPoints = mainViewModel.bulletPoints.value
        assert(bulletPoints!= null && !bulletPoints.isEmpty())

        /**
         * Go back to [com.roldansworkshop.cv.module.home.HomeFragment]
         */
        Log.e("--->", "6")
        onView(isRoot()).perform(ViewActions.pressBack())

        /**
         * Check for [com.roldansworkshop.cv.module.home.HomeFragment]
         */
        Log.e("--->", "7")
        onView(withId(R.id.iv_profile_image)).check(matches(isDisplayed()))

        /**
         * Move to work experience quadrant
         */
        Log.e("--->", "8")
        homeContainer = activityTestRule.activity.findViewById(R.id.ml_home_container)
        homeContainer.transitionToState(R.id.cs_home_top_left)

        /**
         * Go to [com.roldansworkshop.cv.module.experience.work.ProjectsFragment]
         */
        Log.e("--->", "9")
        onView(withId(R.id.b_experience)).perform(click())

        /**
         * Check for [com.roldansworkshop.cv.module.experience.work.ProjectsFragment] and
         * validate data
         */
        Log.e("--->", "10")
        onView(withId(R.id.tv_main_title)).check(matches(withText(appContext.getString(R.string.projects_title))))
        val projects = mainViewModel.projects.value
        assert(projects!= null && !projects.isEmpty())

        /**
         * Go back to [com.roldansworkshop.cv.module.home.HomeFragment]
         */
        Log.e("--->", "11")
        onView(isRoot()).perform(ViewActions.pressBack())

        /**
         * Move to contact quadrant
         */
        Log.e("--->", "12")
        homeContainer = activityTestRule.activity.findViewById(R.id.ml_home_container)
        homeContainer.transitionToState(R.id.cs_home_bottom_left)

        /**
         * Validate correct data is displayed
         */
        Log.e("--->", "13")
        onView(withId(R.id.tv_contact_profile_name)).check(matches(withText(profile!!.name)))
        onView(withId(R.id.tv_location)).check(matches(withText(
            appContext.getString(R.string.contact_info_address, profile.address!!.city, profile.address!!.country)
        )))
        onView(withId(R.id.tv_email)).check(matches(withText(profile.email)))
        onView(withId(R.id.tv_phone_number)).check(matches(withText(
            appContext.getString(R.string.phone_number_uri, profile.phone.countryCode, profile.phone.number)
        )))

    }

}
