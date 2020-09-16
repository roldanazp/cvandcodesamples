package com.roldansworkshop.cv.model

data class Profile(val name: String, val elevatorPitch: String, val image: String,
                   val address: Address?, val email: String, val phone: Phone){
    constructor():this("", "", "", Address(), "", Phone())
}