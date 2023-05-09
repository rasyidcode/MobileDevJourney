package com.rasyidcode.moviefinder.data

import com.rasyidcode.moviefinder.data.repository.SearchMovieRepository
import com.rasyidcode.moviefinder.data.repository.SearchMovieRepositoryImpl
import com.rasyidcode.moviefinder.data.source.SearchMovieDataSource
import com.rasyidcode.moviefinder.data.source.network.SearchMovieDataSourceImpl
import com.rasyidcode.moviefinder.data.source.network.TheMovieDbInterface
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    fun providesSearchMovieDataSource(theMovieDbInterface: TheMovieDbInterface): SearchMovieDataSource {
        return SearchMovieDataSourceImpl(theMovieDbInterface)
    }

    fun providesSearchMovieRepository(searchMovieDataSource: SearchMovieDataSource): SearchMovieRepository {
        return SearchMovieRepositoryImpl(searchMovieDataSource)
    }

}