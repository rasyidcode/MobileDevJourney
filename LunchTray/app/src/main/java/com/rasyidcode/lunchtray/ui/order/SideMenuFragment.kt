package com.rasyidcode.lunchtray.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.lunchtray.R
import com.rasyidcode.lunchtray.databinding.FragmentSideMenuBinding
import com.rasyidcode.lunchtray.model.OrderViewModel

class SideMenuFragment : Fragment() {

    // Binding object instance corresponding to the fragment_side_menu.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentSideMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val orderViewModel by activityViewModels<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSideMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = orderViewModel
            // initialize the EntreeMenuFragment variables
            sideMenuFragment = this@SideMenuFragment
        }
    }

    /**
     * Navigate to the side menu fragment.
     */
    fun goToNextScreen() {
        // Navigate to the SideMenuFragment
        findNavController().navigate(R.id.action_sideMenuFragment_to_accompanimentMenuFragment)
    }

    /**
     * Cancel the order and start over.
     */
    fun cancelOrder() {
        // Reset order in the view model
        orderViewModel.resetOrder()
        // Navigate back to the [StartOrderFragment] to start over
        findNavController().navigate(R.id.action_sideMenuFragment_to_startOrderFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}