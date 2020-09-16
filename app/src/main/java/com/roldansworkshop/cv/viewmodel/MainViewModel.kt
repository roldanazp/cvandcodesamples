package com.roldansworkshop.cv.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.firebase.RemoteConfig
import com.roldansworkshop.cv.firebase.FirebasePath
import com.roldansworkshop.cv.model.BulletPoint
import com.roldansworkshop.cv.model.Profile
import com.roldansworkshop.cv.model.Project
import timber.log.Timber

class MainViewModel: ViewModel() {

    private val doc: DocumentReference by lazy{
        FirebaseFirestore.getInstance().document(FirebasePath.PROFILE_DOC.path)
    }

    val profileLiveData: MutableLiveData<Profile> by lazy{
        MutableLiveData<Profile>().also {
            fetchProfile()
        }
    }

    val bulletPoints: MutableLiveData<List<BulletPoint>> by lazy{
        MutableLiveData<List<BulletPoint>>().also {
            fetchBulletPoints()
        }
    }

    val projects: MutableLiveData<List<Project>> by lazy{
        MutableLiveData<List<Project>>().also {
            fetchProjects()
        }
    }

    val remoteConfig: MutableLiveData<RemoteConfig> by lazy{
        MutableLiveData<RemoteConfig>().also {
            fetchConfigs()
        }
    }

    private fun fetchProfile(){
        doc.get().addOnSuccessListener  { document  ->
            if (document != null) {
                profileLiveData.value = document.toObject(Profile::class.java)
            } else {
                Timber.e("Unable to fetch profile")
            }
        }
    }

    private fun fetchBulletPoints(){
        val colRef = doc.collection(FirebasePath.PROFILE_BLLETPOINTS_COL.node)
        colRef.get().addOnSuccessListener { querySnapshot->
            if (querySnapshot != null) {
                bulletPoints.value = querySnapshot.toObjects(BulletPoint::class.java)
            } else {
                Timber.e("Unable to fetch bullet points")
            }
        }
    }

    private fun fetchProjects(){
        val colRef = doc.collection(FirebasePath.PROFILE_PROJECTS_COL.node)
        colRef.get().addOnSuccessListener { querySnapshot->
            if (querySnapshot != null) {
                projects.value = querySnapshot.toObjects(Project::class.java)
            } else {
                Timber.e("Unable to fetch projects")
            }
        }
    }

    private fun fetchConfigs() {
        val configSettings = remoteConfigSettings {minimumFetchIntervalInSeconds = 3600 }
        Firebase.remoteConfig.setConfigSettingsAsync(configSettings)
        Firebase.remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            remoteConfig.value = RemoteConfig()
            if (!task.isSuccessful){
                Timber.e("Unable to fetch remote configs")
            }
        }
    }

}