package com.rasyidcode.movieapp.ui.review_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.movieapp.databinding.ActivityReviewListBinding

class ReviewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}