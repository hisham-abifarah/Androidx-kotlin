package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api

import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.MovieDetails
import com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Hisham Abi Farah on 04/January/2020

 */

// https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
// https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
// https://api.themoviedb.org/3/

//todo notes retrofit interface includes all the api endpoints we will call

interface TheMovieDBInterface {

    // retrofit annotation GET to get data with URL after the baseURL  "movie/299534"
    // movie_id is a variable so we put it inside {}

    @GET("movie/{movie_id}")

    // function with parameter "id", tell retorift that "id" is movie_id in @GET so we use @PATH
    // returns a Single of type MovieDetails

    // Single is one type of Observable in reactivex or rxjava
    // Observable is a data stream that does  some work and emits data
    // and Observer receives data emitted by the Observable
    // Single emits a single value or an error
    // here we need a Single value : One Movie detail

    fun getMovieDetails(@Path("movie_id")id:Int) : Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page")page:Int, @Query("primary_release_year")year: String) : Single<MovieResponse>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page")page:Int) : Single<MovieResponse>

}