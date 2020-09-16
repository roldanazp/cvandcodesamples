package com.roldansworkshop.cv.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.roldansworkshop.cv.firebase.FirebasePath
import com.roldansworkshop.cv.model.BulletPoint
import com.roldansworkshop.cv.model.Profile
import com.roldansworkshop.cv.model.Project

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

    private fun fetchProfile(){
        doc.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            if (firebaseFirestoreException != null) {
                //TODO error
                profileLiveData.value = Profile()
                return@addSnapshotListener
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                profileLiveData.value = documentSnapshot.toObject(Profile::class.java)
            } else {
                //TODO error
                profileLiveData.value = Profile()
            }

        }
    }

    private fun fetchBulletPoints(){
        val colRef = doc.collection(FirebasePath.PROFILE_BLLETPOINTS_COL.node)
        colRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            if (firebaseFirestoreException != null) {
                //TODO error
                bulletPoints.value = listOf()
                return@addSnapshotListener
            }

            if (querySnapshot != null && !querySnapshot.isEmpty) {
                bulletPoints.value = querySnapshot.toObjects(BulletPoint::class.java)
            } else {
                //TODO error
                bulletPoints.value = listOf()
            }

        }
    }

    private fun fetchProjects(){
        val colRef = doc.collection(FirebasePath.PROFILE_PROJECTS_COL.node)
        colRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            if (firebaseFirestoreException != null) {
                //TODO error
                projects.value = listOf()
                return@addSnapshotListener
            }

            if (querySnapshot != null && !querySnapshot.isEmpty) {
                projects.value = querySnapshot.toObjects(Project::class.java)
            } else {
                //TODO error
                projects.value = listOf()
            }

        }
    }

}