package com.rasyidcode.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rasyidcode.inventory.data.Item
import com.rasyidcode.inventory.data.getFormattedPrice
import com.rasyidcode.inventory.databinding.FragmentItemDetailBinding

class ItemDetailFragment : Fragment() {

    private val viewModel by activityViewModels<InventoryViewModel> {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private val navArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentItemDetailBinding? = null

    private val binding get() = _binding!!

    lateinit var item: Item

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
    }

    /**
     * Displays an alert dialog to get user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
    }

    /**
     * Deletes the current item and navigate to the list fragment
     */
    private fun deleteItem() {
        findNavController().navigateUp()
    }

    private fun bind(item: Item) {
        binding.apply {
            itemName.text = item.itemName
            itemPrice.text = item.getFormattedPrice()
            itemCount.text = item.quantityInStock.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}