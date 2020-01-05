package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.movies_popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.Movie
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hisham Abi Farah on 05/January/2020

 */
class MoviePagedListViewModel(private val movieRepository: MoviePagedListRepository) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}