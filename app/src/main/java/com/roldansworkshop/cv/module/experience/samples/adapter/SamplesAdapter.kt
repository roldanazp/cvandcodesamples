package com.roldansworkshop.cv.module.experience.samples.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roldansworkshop.cv.databinding.SampleItemBinding
import com.roldansworkshop.cv.model.Sample
import com.roldansworkshop.cv.module.experience.samples.presenter.SamplesPresenter

class SamplesAdapter(private val dataset: Array<Sample>, private val presenter: SamplesPresenter): RecyclerView.Adapter<SamplesAdapter.SamplesHolder>() {

    class SamplesHolder(val binding: SampleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SamplesHolder {
        val binding = SampleItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        binding.presenter = presenter
        return SamplesHolder(binding)
    }

    override fun onBindViewHolder(holder: SamplesHolder, position: Int) {
        holder.binding.sample = dataset[position]
    }

    override fun getItemCount() = dataset.size

}