package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    ENDOFLIST
}

//todo notes : constructor for Network State is (val status : String , val message: String)

class NetworkState(val status: Status, val message: String) {

    //todo notes:  use companion object when we want to declare static vars
    companion object {
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST : NetworkState

        // initialize variables in companion object
        init {
            LOADED =
                NetworkState(
                    Status.SUCCESS,
                    "Success"
                )
            LOADING =
                NetworkState(
                    Status.RUNNING,
                    "Running"
                )
            ERROR =
                NetworkState(
                    Status.FAILED,
                    "Something went wrong"
                )
            ENDOFLIST =
                NetworkState(
                    Status.ENDOFLIST,
                    "you have reached the end"
                )
        }
    }
}
