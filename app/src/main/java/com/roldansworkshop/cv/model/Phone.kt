package com.roldansworkshop.cv.model

data class Phone(val countryCode: String, val number: String){
    constructor():this("", "")
    //TODO Validate profile function
}