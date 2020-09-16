package com.roldansworkshop.cv.module.experience.work.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.roldansworkshop.cv.BuildConfig
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.model.Project
import timber.log.Timber
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ProjectBindingAdapter {

    @BindingAdapter("projectDate")
    @JvmStatic
    fun setProjectDates(view: TextView, project: Project) {
        try{
            val dateFormatIn: DateFormat = SimpleDateFormat(BuildConfig.PARSE_DATE_PATTERN, Locale.US)
            val startDate = dateFormatIn.parse(project.start)
            val endDate = dateFormatIn.parse(project.end)
            val dateFormatOut: DateFormat = SimpleDateFormat(BuildConfig.FORMAT_DATE_PATTERN, Locale.US)
            if(startDate!=null && endDate!=null){
                view.text = view.context.getString(R.string.timelapse,
                    dateFormatOut.format(startDate), dateFormatOut.format(endDate))
            }
        }catch(exception: ParseException){
            Timber.e(exception)
        }
    }

}