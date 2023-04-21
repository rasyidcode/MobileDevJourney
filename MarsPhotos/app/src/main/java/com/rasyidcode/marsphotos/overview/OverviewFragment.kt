package com.rasyidcode.marsphotos.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rasyidcode.marsphotos.databinding.FragmentOverviewBinding
import com.rasyidcode.marsphotos.databinding.GridViewItemBinding

class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOverviewBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter()

        viewModel.status.observe(viewLifecycleOwner) {
            Log.d("OverviewFragment", it.name)
        }

        return binding.root
    }


}