package com.rasyidcode.movieapp.ui.movie

import android.content.Context
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
import com.rasyidcode.movieapp.databinding.FragmentNowPlayingBinding

class FragmentNowPlaying : Fragment() {

    private var _binding: FragmentNowPlayingBinding? = null

    private val binding get() = _binding!!

    private val movieViewModel by activityViewModels<MovieViewModel> {
        MovieViewModel.Factory(
            movieRepository = (activity?.application as MovieApplication).movieRepository
        )
    }
    private var lockFetchData = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = movieViewModel
        binding.recyclerView.adapter = MovieListAdapter()
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (!lockFetchData) {
                        Log.d(FragmentPopularMovie.TAG, "Fetching new data..")

                        movieViewModel.fetchNowPlaying()

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

    companion object {
        private const val TAG = "FragmentNowPlaying"
    }

}