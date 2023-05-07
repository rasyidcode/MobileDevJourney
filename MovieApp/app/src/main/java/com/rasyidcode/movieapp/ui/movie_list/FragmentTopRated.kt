package com.rasyidcode.movieapp.ui.movie_list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.databinding.FragmentTopRatedBinding
import com.rasyidcode.movieapp.ui.movie_detail.MovieDetailActivity

class FragmentTopRated : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null

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
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = movieListViewModel
        binding.recyclerView.adapter =
            MovieListAdapter(MovieListActivity.OnMovieItemClick { id, movieId ->
                if (id != null && movieId != null) {
                    startActivity(Intent(context, MovieDetailActivity::class.java).apply {
                        putExtra(MovieDetailActivity.ID, id)
                        putExtra(MovieDetailActivity.MOVIE_ID, movieId)
                        putExtra(
                            MovieDetailActivity.MOVIE_LIST_TYPE,
                            MovieListType.TOP_RATED.name
                        )
                    })
                }

            })
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (!lockFetchData) {
                        Log.d(FragmentPopularMovie.TAG, "Fetching new data..")

                        movieListViewModel.fetchTopRated()

                        lockFetchData = true

                        Handler(Looper.getMainLooper()).postDelayed({
                            lockFetchData = false
                        }, 2000)
                    }
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}