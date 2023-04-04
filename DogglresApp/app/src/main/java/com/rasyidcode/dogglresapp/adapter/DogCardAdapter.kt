package com.rasyidcode.dogglresapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.dogglresapp.R
import com.rasyidcode.dogglresapp.const.Layout
import com.rasyidcode.dogglresapp.data.DataSource
import com.rasyidcode.dogglresapp.model.Dog

class DogCardAdapter(
    private val context: Context?,
    private val layout: Int
) : RecyclerView.Adapter<DogCardAdapter.DogCardViewHolder>() {

    private val dogList: List<Dog> = DataSource.dogs

    class DogCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
        val dogImage = view?.findViewById<ImageView>(R.id.dog_image)
        val dogName = view?.findViewById<TextView>(R.id.dog_name)
        val dogAge = view?.findViewById<TextView>(R.id.dog_age)
        val dogHobbies = view?.findViewById<TextView>(R.id.dog_hobbies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogCardViewHolder {
        val adapterLayout = when(layout) {
            Layout.GRID -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.grid_list_item, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vertical_horizontal_list_item, parent, false)
            }
        }
        return DogCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = dogList.size

    override fun onBindViewHolder(holder: DogCardViewHolder, position: Int) {
        with(holder) {
            with(dogList[position]) {
                dogImage?.setImageResource(imageResourceId)
                dogName?.text = name
                dogAge?.text = context?.getString(R.string.dog_age, age)
                dogHobbies?.text = context?.getString(R.string.dog_hobbies, hobbies)
            }
        }
    }
}