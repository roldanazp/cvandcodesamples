package com.roldansworkshop.cv.module.experience.work

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.roldansworkshop.cv.AbstractFragment
import com.roldansworkshop.cv.R
import com.roldansworkshop.cv.databinding.FragmentListBinding
import com.roldansworkshop.cv.model.Project
import com.roldansworkshop.cv.module.experience.work.adapter.ProjectAdapter
import com.roldansworkshop.cv.presenter.HeaderPresenter
import com.roldansworkshop.cv.viewmodel.MainViewModel

/**
 * Display projects obtained from [MainViewModel]
 */
class ProjectsFragment : AbstractFragment(), HeaderPresenter {

    override val title: Int = R.string.projects_title
    private val shoeViewModel: MainViewModel by activityViewModels()
    private lateinit var binding:FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.header.presenter = this
        observeProjects()
        return binding.root
    }

    private fun observeProjects(){
        shoeViewModel.projects.observe(viewLifecycleOwner, Observer<List<Project>>{ bulletPointList ->
            binding.rvContent.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = ProjectAdapter(bulletPointList)
                binding.mlContainer.transitionToEnd()
            }
        })
    }

    override fun onBackSelected() {
        findNavController().popBackStack()
    }

}