package com.rasyidcode.lunchtray.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rasyidcode.lunchtray.R
import com.rasyidcode.lunchtray.databinding.FragmentStartOrderBinding

/**
 * [StartOrderFragment] allows people to click the start button to start an order.
 */
class StartOrderFragment : Fragment() {

    // Binding object instance corresponding to the fragment_start_order.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks
    // when the view hierarchy is attached to the fragment.
    private var _binding: FragmentStartOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to entree menu
        binding.startOrderBtn.setOnClickListener {
            // navigate to the EntreeMenuFragment
            findNavController().navigate(R.id.action_startOrderFragment_to_entreeMenuFragment)
        }
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