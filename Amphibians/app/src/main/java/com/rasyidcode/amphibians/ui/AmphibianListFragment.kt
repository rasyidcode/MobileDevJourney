package com.rasyidcode.amphibians.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.amphibians.R
import com.rasyidcode.amphibians.databinding.FragmentAmphibianListBinding

private const val TAG = "AmphibianListFragment"

class AmphibianListFragment : Fragment() {

    private val viewModel by activityViewModels<AmphibianViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAmphibianListBinding.inflate(inflater)
        // call the view model method that calls the amphibians api
        viewModel.getAmphibianList()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = AmphibianListAdapter(AmphibianListener { amphibian ->
            Log.d(TAG, "amphibian: $amphibian")
            viewModel.onAmphibianClicked(amphibian)
            findNavController()
                .navigate(R.id.action_amphibianListFragment_to_amphibianDetailFragment)
        })

        return binding.root
    }

}