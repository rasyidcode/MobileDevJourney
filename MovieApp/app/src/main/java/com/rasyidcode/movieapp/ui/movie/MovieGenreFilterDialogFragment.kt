package com.rasyidcode.movieapp.ui.movie

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.databinding.FragmentMovieGenreFilterDialogBinding

class MovieGenreFilterDialogFragment : DialogFragment() {

    private val movieViewModel by activityViewModels<MovieViewModel> {
        MovieViewModel.Factory(
            movieRepository = (activity?.application as MovieApplication).movieRepository
        )
    }

    private var _binding: FragmentMovieGenreFilterDialogBinding? = null
    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        Log.d(TAG, "onCreateView()")
//        _binding = FragmentMovieGenreFilterDialogBinding.inflate(inflater, container, false)
//
//        binding.lifecycleOwner = this
//        binding.viewModel = movieViewModel
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        movieViewModel.selectedGenreIds.observe(this) {
//            Log.d(TAG, "observe selectedGenreIds: $it")
//        }
//
//        Log.d(TAG, "onViewCreated()")
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding =
            FragmentMovieGenreFilterDialogBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this@MovieGenreFilterDialogFragment
        binding.viewModel = movieViewModel

        val builder = activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.filter_genre)
                .setView(binding.root)
                .setPositiveButton("OK") { _, _ ->
                    when (findNavController().currentDestination?.id) {
                        R.id.fragment_popular_movie -> movieViewModel.fetchPopularMovieWithFilter()

                        R.id.fragment_now_playing -> movieViewModel.fetchNowPlaying()

                        R.id.fragment_top_rated -> movieViewModel.fetchTopRated()

                        R.id.fragment_upcoming -> movieViewModel.fetchUpcoming()

                        else -> {}
                    }

                    dismiss()
                }
                .setCancelable(false)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")

        return builder
    }

    override fun onResume() {
        super.onResume()

        val width = resources.getDimensionPixelSize(R.dimen.filter_genre_dialog_width)
        val height = resources.getDimensionPixelSize(R.dimen.filter_genre_dialog_height)

        dialog?.window?.setLayout(width, height)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        const val TAG = "MovieGenreFilterDF"
    }

}