package com.roldansworkshop.cv.model

data class Address(val country: String, val city: String, val location: Location){
    constructor():this("", "", Location())
    //TODO Validate profile function
}