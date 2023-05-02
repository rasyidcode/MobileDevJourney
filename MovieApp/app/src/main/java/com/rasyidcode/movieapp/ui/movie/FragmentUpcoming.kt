package com.rasyidcode.movieapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.databinding.FragmentTopRatedBinding
import com.rasyidcode.movieapp.databinding.FragmentUpcomingBinding

class FragmentUpcoming : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MovieViewModel> {
        MovieViewModel.Factory(
            movieRepository = (activity?.application as MovieApplication).movieRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = MovieListAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}