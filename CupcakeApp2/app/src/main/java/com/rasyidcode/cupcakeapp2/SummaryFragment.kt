package com.rasyidcode.cupcakeapp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.cupcakeapp2.databinding.FragmentSummaryBinding
import com.rasyidcode.cupcakeapp2.model.OrderViewModel

class SummaryFragment : Fragment() {

    private var binding: FragmentSummaryBinding? = null

    private val sharedViewModel by activityViewModels<OrderViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            summaryFragment = this@SummaryFragment
        }
    }

    fun sendOrder() {
        val numberOfCupcakes = sharedViewModel.quantity.value ?: 0
        val flavor = if (sharedViewModel.isMultipleFlavors()) {
            sharedViewModel.flavors.value?.joinToString().toString()
        } else {
            sharedViewModel.flavor.value.toString()
        }
        val orderSummary = getString(
            R.string.order_details,
            sharedViewModel.userName.value.toString(),
            resources.getQuantityString(R.plurals.cupcakes, numberOfCupcakes, numberOfCupcakes),
            flavor,
            sharedViewModel.date.value.toString(),
            sharedViewModel.price.value.toString()
        )
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_LONG).show()
        }
    }

    fun getOrderFlavor(): String {
        return if (sharedViewModel.isMultipleFlavors()) {
            sharedViewModel.flavors.value?.joinToString().toString()
        } else {
            sharedViewModel.flavor.value.toString()
        }
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_summaryFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val LOG = "SummaryFragment"
    }

}