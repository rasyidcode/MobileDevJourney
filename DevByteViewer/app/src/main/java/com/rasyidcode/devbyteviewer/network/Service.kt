package com.rasyidcode.devbyteviewer.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Since we only have one service, this can all go in one file.
// If you add more services, split this to multiple files and make sure to share the retrofit
// object between services.

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface DevByteService {

    @GET("devbytes")
    suspend fun getPlaylist(): NetworkVideoContainer

}

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

/**
 * Main entry point for network access. Call like `DevByteNetwork.devBytes.getPlaylist()
 */
object DevByteNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val devBytes = retrofit.create(DevByteService::class.java)

}