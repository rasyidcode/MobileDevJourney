package com.rasyidcode.forageapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rasyidcode.forageapp.BaseApplication
import com.rasyidcode.forageapp.R
import com.rasyidcode.forageapp.databinding.FragmentAddForageableBinding
import com.rasyidcode.forageapp.model.Forageable
import com.rasyidcode.forageapp.ui.viewmodel.ForageableViewModel
import com.rasyidcode.forageapp.ui.viewmodel.ForageableViewModelFactory

/**
 * A fragment to enter data for a new [Forageable] or edit data for an existing [Forageable].
 * [Forageable]s can be saved or deleted from this fragment.
 */
class AddForageableFragment : Fragment() {

    private val navArgs: AddForageableFragmentArgs by navArgs()

    private var _binding: FragmentAddForageableBinding? = null

    private lateinit var forageable: Forageable

    // This property is only valid between onCreateView and
    // onDestroyView
    private val binding get() = _binding!!

    // Refactor the creation of the view model to take an instance of
    //  ForageableViewModelFactory. The factory should take an instance of the Database retreieved
    //  from BaseApplication
//    private val viewModel by activityViewModels<ForageableViewModel>()
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddForageableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navArgs.id
        if (id > 0) {
            // Observe a Forageable that is retrieved by id, set the forageable variable,
            //  and call the bindForageable method
            viewModel.getForageable(id).observe(viewLifecycleOwner) {
                forageable = it
                bindForageable(forageable)
            }

            binding.btnDelete.visibility = View.VISIBLE
            binding.btnDelete.setOnClickListener { deleteForageable(forageable) }
        } else {
            binding.btnSave.setOnClickListener { addForageable() }
        }
    }

    private fun deleteForageable(forageable: Forageable) {
        viewModel.deleteForageable(forageable)
        findNavController().navigate(R.id.action_addForageableFragment_to_forageableListFragment)
    }

    private fun addForageable() {
        if (isValidEntry()) {
            viewModel.addForageable(
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                inSeason = binding.inSeasonCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    private fun updateForageable() {
        if (isValidEntry()) {
            viewModel.updateForageable(
                id = navArgs.id,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                inSeason = binding.inSeasonCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )
            findNavController().navigate(
                R.id.action_addForageableFragment_to_forageableListFragment
            )
        }
    }

    private fun bindForageable(forageable: Forageable) {
        binding.apply {
            nameInput.setText(forageable.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(forageable.address, TextView.BufferType.SPANNABLE)
            inSeasonCheckbox.isChecked = forageable.inSeason
            notesInput.setText(forageable.notes, TextView.BufferType.SPANNABLE)
            btnSave.setOnClickListener { updateForageable() }
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}