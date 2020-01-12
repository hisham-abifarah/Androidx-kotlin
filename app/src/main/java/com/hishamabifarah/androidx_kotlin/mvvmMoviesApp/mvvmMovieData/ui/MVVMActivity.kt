package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.hishamabifarah.androidx_kotlin.R
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.TheMovieDBClient
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api.TheMovieDBInterface
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository.NetworkState
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.movies_popular.MoviePagedListAdapter
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.movies_popular.MoviePagedListRepository
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.ui.movies_popular.MoviePagedListViewModel
import kotlinx.android.synthetic.main.activity_mvvm.*


class MVVMActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviePagedListViewModel
    lateinit var moviePagedListRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()

        moviePagedListRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = MoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };


        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {

            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            Log.d("hisham- empty list" , viewModel.listIsEmpty().toString())
            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MoviePagedListViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MoviePagedListViewModel(moviePagedListRepository) as T
            }
        })[MoviePagedListViewModel::class.java]
    }
}
