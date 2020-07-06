package com.roldansworkshop.cv.model

import androidx.navigation.NavDirections
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.module.experience.samples.SamplesFragmentDirections

enum class Sample(val titleResourceId: Int, val navDirections: NavDirections) {
    NEON_BUTTON(R.string.neon_button_label, SamplesFragmentDirections.actionSamplesFragmentToSampleNeonButtonActivity())
}