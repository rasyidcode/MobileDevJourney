package com.rasyidcode.cupcakeapp2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.cupcakeapp2.databinding.FragmentFlavorBinding
import com.rasyidcode.cupcakeapp2.model.OrderViewModel

class FlavorFragment : Fragment() {

    private var binding: FragmentFlavorBinding? = null

    private val sharedViewModel by activityViewModels<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFlavorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            flavorFragment = this@FlavorFragment
        }
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_flavorFragment_to_pickupFragment)
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_flavorFragment_to_startFragment)
    }

    fun handleSetFlavor() {
        sharedViewModel.setFlavor(getString(R.string.special_flavor))

        if (sharedViewModel.flavor.value.equals(getString(R.string.special_flavor))) {
            sharedViewModel.updateDateToTomorrow()
        } else {
            sharedViewModel.updateDateToToday()
        }
    }

    fun handleSetFlavors() {
        sharedViewModel.setFlavors(getString(R.string.special_flavor))

        if (sharedViewModel.isFlavorsContain(getString(R.string.special_flavor))) {
            sharedViewModel.updateDateToTomorrow()
        } else {
            sharedViewModel.updateDateToToday()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "FlavorFragment"
    }

}