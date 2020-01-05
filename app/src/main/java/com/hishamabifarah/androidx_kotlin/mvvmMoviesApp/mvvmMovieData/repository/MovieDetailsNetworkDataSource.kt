package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.TheMovieDBInterface
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hisham Abi Farah on 04/January/2020

 */

// call API by using RxJava
// api returns movie details
// then assign movie details in a live data

//CompositeDisposable :rxjava component we use to dispose of our API calls, dispose a rxjava thread


class MovieDetailsNetworkDataSource(
    private val apiInterface: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {


    private val _networkState = MutableLiveData<NetworkState>()
    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()

    // to access network state we use get which only access val networkState not the private one
    val networkState: LiveData<NetworkState>
        get() = _networkState

    val downloadedMovieDetailsResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse


    fun fetchMovieDetails(movieId: Int) {

        _networkState.postValue(NetworkState.LOADING)

        //disposable thread so we add thread to compositeDisposible
        try {
            compositeDisposable.add(
                apiInterface.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io()) // Schedulers.io() is a threadpool , we ar subscribing on this thread pool to observe this network call
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsNetworkDS", it.message)
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("MovieDetailsNetworkDS", e.message)
        }
    }
}