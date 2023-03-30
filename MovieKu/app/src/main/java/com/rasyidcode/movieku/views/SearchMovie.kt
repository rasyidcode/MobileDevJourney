package com.rasyidcode.movieku.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.movieku.adapters.MovieListAdapter
import com.rasyidcode.movieku.adapters.OnMovieItemClickListener
import com.rasyidcode.movieku.api.RequestState
import com.rasyidcode.movieku.databinding.ActivitySearchMovieBinding
import com.rasyidcode.movieku.models.Movie
import com.rasyidcode.movieku.viewmodels.MovieViewModel

class SearchMovie : AppCompatActivity() {

    private var _binding: ActivitySearchMovieBinding? = null

    private val binding get() = _binding

    private val adapter by lazy { MovieListAdapter() }

    private val viewModel by viewModels<MovieViewModel>()

    private var isSearchAgain = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        setupAdapter()
        observeGenreList()
        handleIntentData()
        handleSearchButtonClick()
        handleMovieClick()
    }

    private fun setupAdapter() {
        binding?.apply {
            movieList.adapter = adapter
            movieList.layoutManager = LinearLayoutManager(this@SearchMovie, LinearLayoutManager.VERTICAL, false)
            movieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!movieList.canScrollVertically(1)) {
                        viewModel.searchMovie(binding?.search?.text.toString())
                    }
                }
            })
        }
    }

    private fun handleIntentData() {
        binding?.search?.setText(intent.getStringExtra(query))
        if (!isSearchAgain) viewModel.searchMovie(binding?.search?.text.toString())
    }

    private fun observeGenreList() {
        viewModel.genreList.observe(this) {
            if (it != null) {
                when(it) {
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

                observeSearchMovieList()
            }
        }
    }

    private fun handleSearchButtonClick() {
        binding?.searchButton?.setOnClickListener {
            val query = binding?.search?.text.toString()
            when {
                query.isEmpty() -> binding?.search?.error = "Please insert a keyword"
                else -> {
                    isSearchAgain = true
                    viewModel.searchMovie(query)
                }
            }
        }
    }

    private fun observeSearchMovieList() {
        viewModel.searchMovieList.observe(this) {
            if (it != null) {
                when(it) {
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

    private fun handleMovieClick() {
        adapter.onMovieItemClickListener(object : OnMovieItemClickListener {
            override fun onClick(movie: Movie?, genres: String?) {
                val intent = Intent(this@SearchMovie, MovieDetail::class.java)
                intent.putExtra(MovieDetail.movie, movie)
                intent.putExtra(MovieDetail.genres, genres)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun showLoading() {
        binding?.loading?.show()
    }

    private fun hideLoading() {
        binding?.loading?.hide()
    }

    companion object {
        const val query = "query"
    }

}