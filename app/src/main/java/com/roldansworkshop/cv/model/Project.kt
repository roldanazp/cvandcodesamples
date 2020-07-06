package com.roldansworkshop.cv.model

data class Project(val company: String, val description: String, val jobTitle: String,
                   val location: String, val start: String, val end: String){
    constructor():this("", "", "", "", "", "")
    //TODO Validate profile function
}