package com.rasyidcode.jetinstagram.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rasyidcode.jetinstagram.model.Post

object PostsRepository {

    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: State<List<Post>> = _posts
}