package com.rasyidcode.wordapp3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidcode.wordapp3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        chooseLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu?.findItem(R.id.action_switch_layout)

        setIcon(layoutButton)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager

                chooseLayout()
                setIcon(item)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun chooseLayout() {
        with(binding) {
            recyclerView.layoutManager =
                if (isLinearLayoutManager) {
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                } else {
                    GridLayoutManager(this@MainActivity, 4, GridLayoutManager.VERTICAL, false)
                }
            recyclerView.adapter = LetterAdapter()
        }
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) {
            return
        }

        menuItem.icon = if (isLinearLayoutManager) {
            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_grid_layout)
        } else {
            ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_linear_layout)
        }
    }
}