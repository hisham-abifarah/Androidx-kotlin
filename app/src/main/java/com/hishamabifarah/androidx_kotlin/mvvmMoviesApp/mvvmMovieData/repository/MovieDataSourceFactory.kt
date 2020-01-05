package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.TheMovieDBInterface
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hisham Abi Farah on 05/January/2020

 */

//todo notes implement DataSourceFactory
// class extends DataSource.Factory<T>
// implement its members (one member create())

class MovieDataSourceFactory (private val apiInterface: TheMovieDBInterface ,
                              private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int,Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()


    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiInterface,compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)

        return movieDataSource

    }
}