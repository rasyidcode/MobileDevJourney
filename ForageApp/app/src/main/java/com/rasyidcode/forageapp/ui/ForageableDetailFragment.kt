package com.rasyidcode.forageapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rasyidcode.forageapp.BaseApplication
import com.rasyidcode.forageapp.R
import com.rasyidcode.forageapp.databinding.FragmentForageableDetailBinding
import com.rasyidcode.forageapp.model.Forageable
import com.rasyidcode.forageapp.ui.viewmodel.ForageableViewModel
import com.rasyidcode.forageapp.ui.viewmodel.ForageableViewModelFactory

/**
 * A fragment to display the details of a [Forageable] currently stored in the database.
 * The [AddFOrageableFragment] can be launched from this fragment to edit [Forageable]
 */
class ForageableDetailFragment : Fragment() {

    private val navigationArgs: ForageableDetailFragmentArgs by navArgs()

    // Refactor the create of the view model to take an instance of
    //  ForageableViewModelFactory. The factory should take an instance of the Database retrieved
    //  from BaseApplication
    private val viewModel: ForageableViewModel by activityViewModels {
        ForageableViewModelFactory(
            (activity?.application as BaseApplication).database.forageableDao()
        )
    }

    private var _binding: FragmentForageableDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var forageable: Forageable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForageableDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id

        // Observe a forageable that is retrieved by id, set the forageable variable
        //  and call the bind forageable method
        viewModel.getForageable(id).observe(viewLifecycleOwner) {
            forageable = it
            bindForageable()
        }
    }

    private fun bindForageable() {
        binding.apply {
            name.text = forageable.name
            location.text = forageable.address
            notes.text = forageable.notes
            season.text = if (forageable.inSeason) {
                getString(R.string.in_season)
            } else {
                getString(R.string.out_of_season)
            }
            editForageableFab.setOnClickListener {
                val action =
                    ForageableDetailFragmentDirections.actionForageableDetailFragmentToAddForageableFragment(
                        id = forageable.id
                    )
                findNavController().navigate(action)
            }
            location.setOnClickListener { launchMap() }
        }
    }

    private fun launchMap() {
        val address = forageable.address.let {
            it.replace(", ", ",")
            it.replace(".", "")
            it.replace(" ", "+")
        }
        val gmmIntentUri = Uri.parse("geo:0,0?q=address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}