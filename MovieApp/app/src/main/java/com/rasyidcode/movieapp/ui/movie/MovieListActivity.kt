package com.rasyidcode.movieapp.ui.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.databinding.ActivityMovieListBinding

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding

//    private lateinit var navController: NavController
//
//    private lateinit var appBarConfiguration: AppBarConfiguration
//
//    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        setupNavHost()
//        setupDrawer()
//        setupNavigationView()
    }

//    private fun setupNavHost() {
//        val navHost =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHost.navController
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//    }

//    private fun setupDrawer() {
//        toggle = ActionBarDrawerToggle(
//            this,
//            binding.drawerLayout,
//            binding.toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        binding.drawerLayout.post {
//            toggle.syncState()
//        }
//        binding.drawerLayout.addDrawerListener(toggle)
//        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
//            override fun onDrawerOpened(drawerView: View) {
//                super.onDrawerOpened(drawerView)
//                title = getString(R.string.app_name)
//                toggle.syncState()
//            }
//
//            override fun onDrawerClosed(drawerView: View) {
//                super.onDrawerClosed(drawerView)
//                toggle.syncState()
//            }
//        })
//    }

//    private fun setupNavigationView() {
//        binding.navView.setupWithNavController(navController)
//        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
//    }


//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(
//            navController,
//            binding.drawerLayout
//        ) || super.onSupportNavigateUp()
//    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            binding.drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            @Suppress("DEPRECATION")
//            super.onBackPressed()
//        }
//    }

}