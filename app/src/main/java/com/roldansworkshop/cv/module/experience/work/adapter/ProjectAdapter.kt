package com.roldansworkshop.cv.module.experience.work.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roldansworkshop.cv.BuildConfig
import com.roldansworkshop.cv.databinding.ProjectItemBinding
import com.roldansworkshop.cv.model.Project
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ProjectAdapter(private var dataset: List<Project>): RecyclerView.Adapter<ProjectAdapter.BulletPointHolder>() {

    init {
        val dateFormat: DateFormat = SimpleDateFormat(BuildConfig.PARSE_DATE_PATTERN, Locale.US)
        dataset = dataset.sortedByDescending { dateFormat.parse(it.start) }
    }


    class BulletPointHolder(val binding: ProjectItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulletPointHolder {
        val binding = ProjectItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return BulletPointHolder(binding)
    }

    override fun onBindViewHolder(holder: BulletPointHolder, position: Int) {
        holder.binding.project = dataset[position]
    }

    override fun getItemCount() = dataset.size

}