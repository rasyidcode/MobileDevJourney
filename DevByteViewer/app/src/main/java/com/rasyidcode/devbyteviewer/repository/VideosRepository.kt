package com.rasyidcode.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.rasyidcode.devbyteviewer.database.VideosDatabase
import com.rasyidcode.devbyteviewer.database.asDomainModel
import com.rasyidcode.devbyteviewer.domain.DevByteVideo
import com.rasyidcode.devbyteviewer.network.DevByteNetwork
import com.rasyidcode.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(
    private val database: VideosDatabase
) {

    val videos: LiveData<List<DevByteVideo>> = database.videoDao.getVideos().map {
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = DevByteNetwork.devBytes.getPlaylist()
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }

}