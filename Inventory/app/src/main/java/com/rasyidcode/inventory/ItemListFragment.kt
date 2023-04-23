package com.rasyidcode.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidcode.inventory.databinding.FragmentItemListBinding

class ItemListFragment : Fragment() {

    private val viewModel by activityViewModels<InventoryViewModel> {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding: FragmentItemListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ItemListAdapter {
            val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(
                itemId = it.id
            )
            findNavController().navigate(action)
        }

        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items?.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fab.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                title = getString(R.string.add_fragment_title)
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}