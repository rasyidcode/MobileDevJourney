package com.rasyidcode.amphibians.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rasyidcode.amphibians.databinding.FragmentAmphibianDetailBinding

class AmphibianDetailFragment : Fragment() {

    private val viewModel by activityViewModels<AmphibianViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAmphibianDetailBinding.inflate(inflater)
        // TODO: call the view model method that calls the amphibians api
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}