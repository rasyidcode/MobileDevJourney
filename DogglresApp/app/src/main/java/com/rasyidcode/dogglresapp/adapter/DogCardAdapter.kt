package com.rasyidcode.dogglresapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.dogglresapp.R

class DogCardAdapter(
    private val context: Context?,
    private val layout: Int
) : RecyclerView.Adapter<DogCardAdapter.DogCardViewHolder>() {

    class DogCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val dogImage = view?.findViewById<ImageView>(R.id.dog_image)
        val dogName = view?.findViewById<TextView>(R.id.dog_name)
        val dogAge = view?.findViewById<TextView>(R.id.dog_age)
        val dogHobbies = view?.findViewById<TextView>(R.id.dog_hobbies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogCardViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DogCardViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}