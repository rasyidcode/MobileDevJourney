package com.rasyidcode.wordsapp2

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidcode.wordsapp2.databinding.FragmentLetterListBinding

class LetterListFragment : Fragment() {

    private var _binding: FragmentLetterListBinding? = null

    private val binding get() = _binding!!

    private var isLinearLayoutManager = true

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

        chooseLayout()
        setupOptionsMenu()
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