package com.rasyidcode.trainingsports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.rasyidcode.trainingsports.databinding.FragmentSportsNewsBinding

class NewsDetailFragment : Fragment() {

    private val sportsViewModel by activityViewModels<SportsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSportsNewsBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSportsNewsBinding.bind(view)

        sportsViewModel.currentSport.observe(viewLifecycleOwner) {
            binding.titleDetail.text = getString(it.titleResourceId)
            binding.sportsImageDetail.load(it.sportsImageBanner)
            binding.newsDetail.text = getString(it.newsDetail)
        }
    }

}