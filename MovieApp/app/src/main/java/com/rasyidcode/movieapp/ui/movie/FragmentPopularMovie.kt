package com.rasyidcode.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.databinding.FragmentPopularMovieBinding

class FragmentPopularMovie : Fragment() {

    private var _binding: FragmentPopularMovieBinding? = null

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
        _binding = FragmentPopularMovieBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = MovieListAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FragmentPopularMovie"
    }

}