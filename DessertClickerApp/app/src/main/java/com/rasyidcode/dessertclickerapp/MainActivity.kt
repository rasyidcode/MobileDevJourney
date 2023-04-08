package com.rasyidcode.dessertclickerapp

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.rasyidcode.dessertclickerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var revenue = 0
    private var dessertSold = 0

    private lateinit var binding: ActivityMainBinding

    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    private val allDeserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )

    private var currentDesert = allDeserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate() called")

        binding = DataBindingUtil.setContentView(
            this@MainActivity,
            R.layout.activity_main
        )

        if (savedInstanceState != null) {
            revenue = savedInstanceState.getInt(KEY_REVENUE, 0)
            dessertSold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
            showCurrentDessert()
        }

        binding.dessertButton.setOnClickListener {
            onDesertClicked()
        }

        binding.revenue = revenue
        binding.amountSold = dessertSold

        binding.dessertButton.setImageResource(currentDesert.imageId)
    }

    private fun onDesertClicked() {
        revenue += currentDesert.price
        dessertSold++

        binding.revenue = revenue
        binding.amountSold = dessertSold

        showCurrentDessert()
    }

    private fun showCurrentDessert() {
        var newDessert = allDeserts[0]
        for (dessert in allDeserts) {
            if (dessertSold >= dessert.startProductionAmount) {
                newDessert = dessert
            } else break
        }

        if (newDessert != currentDesert) {
            currentDesert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onShare() {
        var shareIntent = ShareCompat.IntentBuilder(this@MainActivity)
            .setText(getString(R.string.share_text, dessertSold, revenue))
            .setType("text/plain")
            .intent

        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available), Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy() called")
    }

    override fun onRestart() {
        super.onRestart()

        Log.d(TAG, "onRestart() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERT_SOLD, dessertSold)

        Log.d(TAG, "onSaveInstanceState() called")
    }

    companion object {
        const val TAG = "MainActivity"
        const val KEY_REVENUE = "revenue_key"
        const val KEY_DESSERT_SOLD = "dessert_sold_key"
    }
}