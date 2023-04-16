package com.rasyidcode.lunchtray.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.rasyidcode.lunchtray.R
import com.rasyidcode.lunchtray.databinding.FragmentCheckoutBinding
import com.rasyidcode.lunchtray.model.OrderViewModel

class CheckoutFragment : Fragment() {

    // Binding object instance corresponding to the fragment_accompaniment_menu.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentCheckoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val orderViewModel by activityViewModels<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            // TODO: initialize the orderViewModel and the EntreeMenuFragment variables
        }
    }

    /**
     * Submit order and navigate to home screen.
     */
    fun submitOrder() {
        // Show snackbar to "confirm" order
        Snackbar.make(binding.root, R.string.submit_order, Snackbar.LENGTH_SHORT).show()
        // TODO: Reset order in the view model
        // TODO: Navigate back to the [StartOrderFragment] to start over
    }

    /**
     * Cancel the order and start over.
     */
    fun cancelOrder() {
        // TODO: Reset order in the view model
        // TODO: Navigate back to the [StartOrderFragment] to start over
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