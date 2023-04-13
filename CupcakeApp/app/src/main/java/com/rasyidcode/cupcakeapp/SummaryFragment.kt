package com.rasyidcode.cupcakeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rasyidcode.cupcakeapp.databinding.FragmentSummaryBinding
import com.rasyidcode.cupcakeapp.model.OrderViewModel

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
        Toast.makeText(activity, "Send Order", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}