package com.rasyidcode.movieku.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.rasyidcode.movieku.adapters.MovieListAdapter
import com.rasyidcode.movieku.adapters.OnMovieItemClickListener
import com.rasyidcode.movieku.api.RequestState
import com.rasyidcode.movieku.databinding.ActivityMovieListBinding
import com.rasyidcode.movieku.models.Movie
import com.rasyidcode.movieku.viewmodels.MovieViewModel

class MovieList : AppCompatActivity() {

    private var _binding: ActivityMovieListBinding? = null
    private val binding get() = _binding

    private val adapter by lazy { MovieListAdapter() }

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupAdapter()
        observeGenreList()
        handleMovieClick()
        handleSearch()
    }

    private fun handleSearch() {
        binding?.searchButton?.setOnClickListener {
            val query = binding?.search?.text.toString()
            when {
                query.isEmpty() -> binding?.search?.error = "Please insert a keyword"
                else -> {
                    val intent = Intent(this@MovieList, SearchMovie::class.java)
                    intent.putExtra(SearchMovie.query, query)
                    startActivity(intent)
                }
            }
        }
    }

    private fun observeMovieList() {
        viewModel.movieList.observe(this) {
            if (it != null) {
                when (it) {
                    is RequestState.Loading -> showLoading()
                    is RequestState.Success -> {
                        hideLoading()

                        it.data?.results?.let { data -> adapter.differ.submitList(data.toList()) }
                    }
                    is RequestState.Error -> {
                        hideLoading()

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun observeGenreList() {
        viewModel.genreList.observe(this) {
            if (it != null) {
                when (it) {
                    is RequestState.Success -> {
                        adapter.setGenreList(it.data?.genres ?: ArrayList())
                    }
                    is RequestState.Error -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(this, "Unknown State", Toast.LENGTH_LONG).show()
                    }
                }

                observeMovieList()
            }
        }
    }

    private fun setupAdapter() {
        binding?.apply {
            movieList.adapter = adapter
            movieList.layoutManager =
                LinearLayoutManager(this@MovieList, LinearLayoutManager.VERTICAL, false)
            movieList.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!movieList.canScrollVertically(1)) {
                        Log.d("MovieList", "scroll")
                        viewModel.getNextPopularMovie()
                    }
                }
            })
        }
    }

    private fun handleMovieClick() {
        adapter.onMovieItemClickListener(object : OnMovieItemClickListener {
            override fun onClick(movie: Movie?, genres: String?) {
                val intent = Intent(this@MovieList, MovieDetail::class.java)
                intent.putExtra(MovieDetail.movie, movie)
                intent.putExtra(MovieDetail.genres, genres)
                startActivity(intent)
            }
        })
    }

    private fun showLoading() {
        binding?.loading?.show()
    }

    private fun hideLoading() {
        binding?.loading?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}