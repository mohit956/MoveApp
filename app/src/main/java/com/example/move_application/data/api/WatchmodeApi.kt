package com.example.move_application.data.api

import com.example.move_application.data.model.response.WatchContentListResponse
import com.example.move_application.data.model.response.WatchContentResponse
import io.reactivex.rxjava3.core.Single
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchmodeApi {

    @GET("movie/popular")
    fun getMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Single<WatchContentListResponse>

    @GET("tv/popular")
    fun getTvShows(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Single<WatchContentListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US"
    ): WatchContentResponse

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String = "en-US"
    ): WatchContentResponse
}
