package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.FIRST_PAGE
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.TheMovieDBInterface
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hisham Abi Farah on 05/January/2020

 */

//todo notes: PageKeyedDataSouce use to paginate, it's from android paging library
// override methods : either with ctrl + o or alt+enter then implement members
// check how to use paging library with multiple view type : https://proandroiddev.com/android-paging-library-with-multiple-view-type-68f85fe1222d

class MovieDataSource(
    private val apiInterface: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        // intitial load of data
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiInterface.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.movieList,null,page+1)
                    networkState.postValue(NetworkState.LOADED)
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource" , it.message)
                })
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // load next data on scroll
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiInterface.getPopularMovies(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe({

                    if(it.totalPages >= params.key){
                        callback.onResult(it.movieList,params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    }else{
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }

                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource" , it.message)
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // when user scrolls up, but not needed because recyclerview will hold previous data
    }


}