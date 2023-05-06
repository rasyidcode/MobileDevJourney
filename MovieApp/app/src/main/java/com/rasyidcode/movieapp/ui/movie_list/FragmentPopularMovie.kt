package com.rasyidcode.movieapp.ui.movie_list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.databinding.FragmentPopularMovieBinding

class FragmentPopularMovie : Fragment() {

    private var _binding: FragmentPopularMovieBinding? = null

    private val binding get() = _binding!!

    private val movieListViewModel by activityViewModels<MovieListViewModel> {
        MovieListViewModel.Factory(
            movieRepository = (activity?.application as MovieApplication).movieRepository
        )
    }

    private var lockFetchData = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularMovieBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = movieListViewModel
            recyclerView.adapter = MovieListAdapter()
            recyclerView.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1)) {
                        if (!lockFetchData) {
                            movieListViewModel.selectedGenreIdsSize.observe(viewLifecycleOwner) { genreFilterSize ->
                                if (genreFilterSize > 0) {
                                    movieListViewModel.fetchPopularMovieWithFilter(keepFilter = true)
                                } else {
                                    movieListViewModel.fetchPopularMovies()
                                }
                            }

                            lockFetchData = true

                            Handler(Looper.getMainLooper()).postDelayed({
                                lockFetchData = false
                            }, 2000)
                        }
                    }
                }
            })
        }

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