package com.rasyidcode.dogglresapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonVertical = findViewById<Button>(R.id.btn_vertical)
        buttonVertical.setOnClickListener {
            startActivity(Intent(this@MainActivity, VerticalListActivity::class.java))
        }

        val buttonHorizontal = findViewById<Button>(R.id.btn_horizontal)
        buttonHorizontal.setOnClickListener {
            startActivity(Intent(this@MainActivity, HorizontalListActivity::class.java))
        }

        val buttonGrid = findViewById<Button>(R.id.btn_grid)
        buttonGrid.setOnClickListener {
            startActivity(Intent(this@MainActivity, GridActivity::class.java))
        }
    }
}