package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.TheMovieDBInterface
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.MovieDetails
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.MovieDetailsNetworkDataSource
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hisham Abi Farah on 04/January/2020

 */
class MovieDetailsRepository (private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchingSingleMovieDetails(compositeDisposable: CompositeDisposable,movieId:Int) : LiveData<MovieDetails>{

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieDetailsNetworkState() : LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}