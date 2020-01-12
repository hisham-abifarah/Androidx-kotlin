package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.api

import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Hisham Abi Farah on 04/January/2020

 */

const val API_KEY = "6389d6c35aa29facb5a46bf0d2e81227"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

const val FIRST_PAGE = 499
const val POST_PER_PAGE = 20

object TheMovieDBClient {

    fun getClient(): TheMovieDBInterface {

        // create Interceptor to put api key in the URL
        val requestInterceptor = Interceptor { chain ->
            // Interceptor take only one argument which is a lambda function so parenthesis can be omitted

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
//                .addQueryParameter("primary_release_year", "1899")
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request) //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
        }

        val mLoggingInterceptor = HttpLoggingInterceptor()
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addNetworkInterceptor(mLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDBInterface::class.java)
    }
}