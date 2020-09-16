package com.roldansworkshop.cv.model

data class Location(val latitude: Float, val longitude: Float){
    constructor():this(0F, 0F)
}