package com.rasyidcode.waterme

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.waterme.adapter.PlantAdapter
import com.rasyidcode.waterme.adapter.PlantListener
import com.rasyidcode.waterme.viewmodel.PlantViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PlantViewModel by viewModels {
        PlantViewModel.PlantViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PlantAdapter(PlantListener { plant ->
            val dialog = ReminderDialogFragment(plant.name)
            dialog.show(supportFragmentManager, "WaterReminderDialogFragment")
            true
        })
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        val data = viewModel.plants
        adapter.submitList(data)
    }

}