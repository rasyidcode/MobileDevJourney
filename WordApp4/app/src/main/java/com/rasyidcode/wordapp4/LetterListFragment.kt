package com.rasyidcode.wordapp4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidcode.wordapp4.data.SettingsDataSource
import com.rasyidcode.wordapp4.databinding.FragmentLetterListBinding
import kotlinx.coroutines.launch

class LetterListFragment : Fragment() {

    private var _binding: FragmentLetterListBinding? = null

    private val binding get() = _binding!!

    private var isLinearLayoutManager = true

    private lateinit var SettingsDataSource: SettingsDataSource

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOptionsMenu()

        // Initialize SettingsDataSource
        SettingsDataSource = SettingsDataSource(requireContext())
        SettingsDataSource.preferenceFlow.asLiveData().observe(viewLifecycleOwner) { value ->
            isLinearLayoutManager = value
            chooseLayout()
            // Redraw the menu
            activity?.invalidateOptionsMenu()
        }
    }

    private fun setupOptionsMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.layout_menu, menu)

                setIcon(menu.findItem(R.id.action_switch_layout))
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_switch_layout -> {
                        isLinearLayoutManager = !isLinearLayoutManager

                        chooseLayout()
                        setIcon(menuItem)

                        // Launch a coroutine and write the layout setting in the preference DataStore
                        lifecycleScope.launch {
                            SettingsDataSource.saveLayoutToPreferencesStore(
                                isLinearLayoutManager,
                                requireContext()
                            )
                        }

                        return true
                    }

                    else -> true
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) {
            return
        }

        menuItem.icon = if (isLinearLayoutManager) {
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
        } else {
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
        }
    }

    private fun chooseLayout() {
        with(binding) {
            recyclerView.layoutManager =
                if (isLinearLayoutManager) {
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                } else {
                    GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
                }
            recyclerView.adapter = LetterAdapter()
        }
    }

}