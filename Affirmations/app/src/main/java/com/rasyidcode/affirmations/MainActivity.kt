package com.rasyidcode.affirmations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.affirmations.adapter.ItemAdapter
import com.rasyidcode.affirmations.data.DataSource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDataset = DataSource().loadAffirmations()
        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerview.adapter = ItemAdapter(this, myDataset)
        recyclerview.setHasFixedSize(true)
    }
}