package com.rasyidcode.remarsphotos_dotaheroes

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rasyidcode.remarsphotos_dotaheroes.herolist.HeroListAdapter
import com.rasyidcode.remarsphotos_dotaheroes.herolist.OpenDotaApiStatus
import com.rasyidcode.remarsphotos_dotaheroes.network.DotaHero

@BindingAdapter("heroList")
fun bindHeroList(recyclerView: RecyclerView, heroes: List<DotaHero>?) {
    val adapter = recyclerView.adapter as HeroListAdapter
    heroes?.let {
        adapter.submitList(it)
    }
}


private const val imageBaseURL = "https://cdn.cloudflare.steamstatic.com"
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = "${imageBaseURL}${imageUrl}".toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("apiStatus")
fun bindApiStatus(progressBar: ProgressBar, status: OpenDotaApiStatus?) {
    when (status) {
        OpenDotaApiStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        else -> {
            progressBar.visibility = View.GONE
        }
    }
}