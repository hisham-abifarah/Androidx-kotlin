package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.movies_popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.POST_PER_PAGE
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.TheMovieDBInterface
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.Movie
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.MovieDataSource
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.MovieDataSourceFactory
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Hisham Abi Farah on 05/January/2020

 */
class MoviePagedListRepository (private val apiInterface : TheMovieDBInterface) {

    lateinit var movieDataSourceFactory : MovieDataSourceFactory
    lateinit var moviePagedList: LiveData<PagedList<Movie>>

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiInterface,compositeDisposable)

        //configure page list
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory,config).build()
        return moviePagedList
    }

    fun getNetworkState() : LiveData<NetworkState>{
        //networkState is inside MovieDataSouce
      // access networkstate mutable llivedata and transforming it to
        // livedata from this movieLiveDataSource livedata

        return Transformations.switchMap(MovieDataSource,NetworkState)(
            movieDataSourceFactory.movieLiveDataSource,MovieDataSource::networkState)
    }
}