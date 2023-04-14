package com.rasyidcode.cupcakeapp2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.cupcakeapp2.databinding.FragmentFlavorBinding
import com.rasyidcode.cupcakeapp2.databinding.FragmentUserNameBinding
import com.rasyidcode.cupcakeapp2.model.OrderViewModel

class UserNameFragment : Fragment() {

    private var binding: FragmentUserNameBinding? = null

    private val sharedViewModel by activityViewModels<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserNameBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            userNameFragment = this@UserNameFragment
        }
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_userNameFragment_to_summaryFragment)
    }

    fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_userNameFragment_to_startFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}