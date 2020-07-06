package com.roldansworkshop.cv.module.home.presenter

import com.roldansworkshop.cv.presenter.Presenter

interface ContactPresenter: Presenter {
    fun onPhoneNumberSelected();
    fun onEmailSelected();
    fun onLocationSelected();
}