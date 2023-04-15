package com.rasyidcode.trainingsports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.rasyidcode.trainingsports.databinding.FragmentSportsListBinding

class SportsListFragment : Fragment() {

    private val sportsViewModel by activityViewModels<SportsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSportsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSportsListBinding.bind(view)

        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            SportsListOnBackPressedCallback(slidingPaneLayout)
        )

        val adapter = SportsAdapter {
            sportsViewModel.updateCurrentSport(it)

//            val action = SportsListFragmentDirections.actionSportsListFragmentToNewsDetailFragment()
//            this.findNavController().navigate(action)
            binding.slidingPaneLayout.openPane()
        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(sportsViewModel.sportsData)
    }

}

class SportsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        isEnabled = false
    }

}