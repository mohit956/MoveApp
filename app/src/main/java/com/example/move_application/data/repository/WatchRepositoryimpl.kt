package com.example.move_application.data.repository

import com.example.move_application.data.api.WatchmodeApi
import com.example.move_application.data.mapper.WatchMapper
import com.example.move_application.data.model.WatchContent
import io.reactivex.rxjava3.core.Single

    class WatchRepositoryImpl(
        private val api: WatchmodeApi,
        private val mapper: WatchMapper
    ) : WatchRepository {

        override fun getMoviesAndTvShows(): Single<Pair<List<WatchContent>, List<WatchContent>>> {
            return Single.zip(
                api.getMovies()
                    .map { mapper.mapResponseListToWatchContent(it.results) },
                api.getTvShows()
                    .map { mapper.mapResponseListToWatchContent(it.results) }
            ) { movies, tvShows ->
                Pair(movies,tvShows)
            }
        }

        override suspend fun getContentDetails(contentId: String, isMovie: Boolean): WatchContent {
            return if (isMovie) {
                mapper.mapResponseToWatchContent(api.getMovieDetails(contentId.toInt()))
            } else {
                mapper.mapResponseToWatchContent(api.getTvShowDetails(contentId.toInt()))
            }
        }
    }


