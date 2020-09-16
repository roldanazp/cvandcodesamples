package com.roldansworkshop.cv.module.experience.samples

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.databinding.FragmentListBinding
import com.roldansworkshop.cv.model.Project
import com.roldansworkshop.cv.model.Sample
import com.roldansworkshop.cv.module.experience.samples.adapter.SamplesAdapter
import com.roldansworkshop.cv.module.experience.samples.presenter.SamplesPresenter
import com.roldansworkshop.cv.module.experience.work.adapter.ProjectAdapter
import com.roldansworkshop.cv.presenter.HeaderPresenter

class SamplesFragment : Fragment(), HeaderPresenter, SamplesPresenter {

    override val title: Int = R.string.samples_title
    private lateinit var binding:FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.header.presenter = this
        setUpSamples()
        return binding.root
    }

    private fun setUpSamples() = binding.rvContent.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = SamplesAdapter(Sample.values(), this@SamplesFragment)
            binding.mlContainer.transitionToEnd()
        }

    override fun onBackSelected(): Boolean = findNavController().popBackStack()

    override fun onSampleSelected(sample: Sample) = findNavController().navigate(sample.navDirections)

}