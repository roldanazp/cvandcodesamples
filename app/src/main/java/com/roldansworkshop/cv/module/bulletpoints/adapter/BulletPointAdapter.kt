package com.roldansworkshop.cv.module.bulletpoints.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roldansworkshop.cv.databinding.BulletPointItemBinding
import com.roldansworkshop.cv.model.BulletPoint

class BulletPointAdapter(private val dataset: Array<BulletPoint>): RecyclerView.Adapter<BulletPointAdapter.BulletPointHolder>() {

    class BulletPointHolder(val binding: BulletPointItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulletPointHolder {
        val binding = BulletPointItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return BulletPointHolder(binding)
    }

    override fun onBindViewHolder(holder: BulletPointHolder, position: Int) {
        holder.binding.bulletPoint = dataset[position]
    }

    override fun getItemCount() = dataset.size

}