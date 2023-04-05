package com.rasyidcode.wordsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidcode.wordsapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val letterId = "A"

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(this@DetailActivity)
            recyclerView.adapter = WordAdapter(letterId, this@DetailActivity)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    this@DetailActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        title = getString(R.string.detail_prefix) + " " + letterId
    }

}