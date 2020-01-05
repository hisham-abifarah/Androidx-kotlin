package com.hishamabifarah.androidx_kotlin.mvvmMoviesApp.mvvmMovieData.model


import com.google.gson.annotations.SerializedName

// todo notes: data class means no getters and setters like in JAVA
// todo notes: @SerializedName annotation to tell GSON that different variable names are the same (example: original_title and originalTitle are the same) etc..
// todo implement ProductionCompany class which is a List here
// todo parcelable data classes implement and why?

data class MovieDetails(

    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val rating: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("budget") val budget: Int
)