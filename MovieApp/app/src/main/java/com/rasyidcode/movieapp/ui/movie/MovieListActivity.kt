package com.rasyidcode.movieapp.ui.movie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.databinding.ActivityMovieListBinding

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding

    private lateinit var navController: NavController

    private lateinit var toggle: ActionBarDrawerToggle

    private var doubleBackToExistPressedOnce = false

    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModel.Factory(
            movieRepository = (application as MovieApplication).movieRepository
        )
    }

    private var isDrawerOpened = false
    private var isNowPlayingVisible = false
    private var isTopRatedVisible = false
    private var isUpcomingVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupNavHost()
        setupToolbar()
        setupDrawer()
        setupNavigation()
        setupHandleBackPressed()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_now_playing -> {
                    if (!isNowPlayingVisible) {
                        viewModel.fetchNowPlaying()
                        isNowPlayingVisible = true
                    }
                }

                R.id.fragment_top_rated -> {
                    if (!isTopRatedVisible) {
                        viewModel.fetchTopRated()
                        isTopRatedVisible = true
                    }
                }

                R.id.fragment_upcoming -> {
                    if (!isUpcomingVisible) {
                        viewModel.fetchUpcoming()
                        isUpcomingVisible = true
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.popular)

        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupNavHost() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController
    }

    private fun setupDrawer() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.post {
            toggle.syncState()
        }
        binding.drawerLayout.addDrawerListener(toggle)
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                if (!isDrawerOpened) {
                    Log.d(TAG, "fetchLatestMovie()")
                    viewModel.fetchLatestMovie()
                    isDrawerOpened = true
                }

                supportActionBar?.title = getString(R.string.app_name)
                toggle.syncState()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

                supportActionBar?.title =
                    when (navController.currentDestination?.id) {
                        R.id.fragment_popular_movie -> getString(R.string.popular)
                        R.id.fragment_now_playing -> getString(R.string.now_playing)
                        R.id.fragment_top_rated -> getString(R.string.top_rated)
                        R.id.fragment_upcoming -> getString(R.string.upcoming)
                        else -> getString(R.string.app_name)
                    }
                toggle.syncState()
            }
        })
    }

    private fun setupNavigation() {
        binding.navView.setupWithNavController(navController)
        binding.navView.setCheckedItem(R.id.fragment_popular_movie)
    }

    private fun setupHandleBackPressed() {
        onBackPressedDispatcher.addCallback(this) {
            when (navController.currentDestination?.id) {
                R.id.fragment_popular_movie -> {
                    if (doubleBackToExistPressedOnce) {
                        finish()
                        return@addCallback
                    }

                    doubleBackToExistPressedOnce = true

                    Toast.makeText(
                        this@MovieListActivity,
                        getString(R.string.message_before_quit),
                        Toast.LENGTH_LONG
                    ).show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackToExistPressedOnce = false
                    }, 2000)
                }

                R.id.fragment_now_playing -> {
                    navController.navigate(R.id.action_fragment_now_playing_to_fragment_popular_movie)
                }

                R.id.fragment_top_rated -> {
                    navController.navigate(R.id.action_fragment_top_rated_to_fragment_popular_movie)
                }

                R.id.fragment_upcoming -> {
                    navController.navigate(R.id.action_fragment_upcoming_to_fragment_popular_movie)
                }

                else -> {}
            }
        }
    }

    companion object {
        const val TAG = "MovieListActivity"
    }

}