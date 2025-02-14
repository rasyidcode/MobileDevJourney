package com.rasyidcode.cupcakeapp2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.cupcakeapp2.databinding.FragmentPickupBinding
import com.rasyidcode.cupcakeapp2.model.OrderViewModel

class PickupFragment : Fragment() {

    private var binding: FragmentPickupBinding? = null

    private val sharedViewModel by activityViewModels<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPickupBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            pickupFragment = this@PickupFragment
        }
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickupFragment_to_userNameFragment)
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_pickupFragment_to_startFragment)
    }

    fun isFlavorCanBeSameDay(): Boolean {
        Log.d(TAG, "${sharedViewModel.isFlavorsContain(getString(R.string.special_flavor))}")
        return if (sharedViewModel.isQuantityMoreThanOne()) {
            sharedViewModel.isFlavorsContain(getString(R.string.special_flavor))
        } else {
            sharedViewModel.flavor.value?.equals(getString(R.string.special_flavor)) ?: false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val TAG = "PickupFragment"
    }

}