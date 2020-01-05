package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.MovieDetails
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hisham Abi Farah on 04/January/2020

 */
class SingleMovieViewModel (private val movieRepository: MovieDetailsRepository , movieId: Int): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchingSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    // val movieDetails can be written if not by lazy like :
    val movieDetails2: LiveData<MovieDetails> = movieRepository.fetchingSingleMovieDetails(compositeDisposable,movieId)

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

