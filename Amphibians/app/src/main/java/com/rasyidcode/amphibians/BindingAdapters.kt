package com.rasyidcode.amphibians

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.amphibians.network.Amphibian
import com.rasyidcode.amphibians.ui.AmphibianApiStatus
import com.rasyidcode.amphibians.ui.AmphibianListAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Amphibian>?) {
    val adapter = recyclerView.adapter as AmphibianListAdapter
    adapter.submitList(data)
}

@BindingAdapter("amphibianApiStatus")
fun bindStatus(imageView: ImageView, status: AmphibianApiStatus?) {
    when (status) {
        AmphibianApiStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_img)
        }

        AmphibianApiStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
        }

        AmphibianApiStatus.DONE -> {
            imageView.visibility = View.GONE
        }

        else -> {}
    }
}