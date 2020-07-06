package com.roldansworkshop.cv.module.experience.work.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roldansworkshop.cv.databinding.ProjectItemBinding
import com.roldansworkshop.cv.model.Project

class ProjectAdapter(private val dataset: Array<Project>): RecyclerView.Adapter<ProjectAdapter.BulletPointHolder>() {

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