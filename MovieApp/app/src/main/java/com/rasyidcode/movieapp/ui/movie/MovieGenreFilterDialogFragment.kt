package com.rasyidcode.movieapp.ui.movie

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.databinding.FragmentMovieGenreFilterDialogBinding

class MovieGenreFilterDialogFragment : DialogFragment() {

    private val movieViewModel by activityViewModels<MovieViewModel> {
        MovieViewModel.Factory(
            movieRepository = (activity?.application as MovieApplication).movieRepository
        )
    }

    private var _binding: FragmentMovieGenreFilterDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.viewModel = movieViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentMovieGenreFilterDialogBinding.inflate(layoutInflater)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(R.string.filter_genre)
                .setView(binding.root)
                .setPositiveButton("Filter") { _, _ ->
                    when (findNavController().currentDestination?.id) {
                        R.id.fragment_popular_movie -> {
                            movieViewModel.fetchPopularMovies()
                        }

                        else -> {}
                    }

                    dismiss()
                }
                .setNegativeButton("Clear Filter") { _, _ ->
                    movieViewModel.clearFilters()
                }
                .setCancelable(false)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}