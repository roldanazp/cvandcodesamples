package com.roldansworkshop.cv.module.bulletpoints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.roldansworkshop.cv.AbstractFragment
import com.roldansworkshop.cv.module.bulletpoints.adapter.BulletPointAdapter
import com.roldansworkshop.cv.presenter.HeaderPresenter
import com.roldansworkshop.cv.viewmodel.MainViewModel
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.databinding.FragmentListBinding

/**
 * Display bullet points obtained from [MainViewModel]
 */
class BulletPointsFragment : AbstractFragment(), HeaderPresenter {

    override val title: Int = R.string.bullet_points_label
    private val shoeViewModel: MainViewModel by activityViewModels()
    private lateinit var binding:FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.header.presenter = this
        observeBulletPointsList()
        return binding.root
    }

    private fun observeBulletPointsList(){
        shoeViewModel.bulletPoints.observe(viewLifecycleOwner, Observer{ bulletPointList ->
            binding.rvContent.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = BulletPointAdapter(bulletPointList.toTypedArray())
                binding.mlContainer.transitionToEnd()
                /**
                 * TESTING
                 * Release testing idling resource
                 */
                this@BulletPointsFragment.decrement(BulletPointsFragment::class.qualifiedName)
            }
        })
    }

    override fun onBackSelected() {
        findNavController().popBackStack()
    }

}