package com.rasyidcode.movieapp.ui.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.databinding.ActivityMovieListBinding

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding

    private lateinit var navController: NavController

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavHost()
        setupToolbar()
        setupDrawer()
        setupNavigation()
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

                supportActionBar?.title = getString(R.string.app_name)
                toggle.syncState()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

                supportActionBar?.title =
                    when (navController.currentDestination?.id) {
                        R.id.fragmentPopularMovie -> getString(R.string.popular)
                        R.id.fragmentNowPlaying -> getString(R.string.now_playing)
                        R.id.fragmentTopRated -> getString(R.string.top_rated)
                        R.id.fragmentUpcoming -> getString(R.string.upcoming)
                        else -> getString(R.string.app_name)
                    }
                toggle.syncState()
            }
        })
    }

    private fun setupNavigation() {
        binding.navView.setupWithNavController(navController)
        binding.navView.setCheckedItem(R.id.menu_popular)
//        binding.navView.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.menu_popular -> {
//                    navController.navigate
//                }
//            }
//            true
//        }
    }

    companion object {
        const val TAG = "MovieListActivity"
    }

}