package com.example.move_application

import com.example.move_application.data.api.WatchmodeApi
import com.example.move_application.data.mapper.WatchMapper
import com.example.move_application.data.model.WatchContent
import com.example.move_application.data.model.response.WatchContentListResponse
import com.example.move_application.data.model.response.WatchContentResponse
import com.example.move_application.data.repository.WatchRepositoryImpl
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class WatchRepositoryImplTest {

    private lateinit var api: WatchmodeApi
    private lateinit var repository: WatchRepositoryImpl

    // Fake mapper
    private val fakeMapper = object : WatchMapper() {
        override fun mapResponseListToWatchContent(input: List<WatchContentResponse>): List<WatchContent> {
            return input.map {
                WatchContent(
                    id = it.id,
                    title = it.title ?: "Unknown",
                    posterUrl = "",
                    description = it.overview,
                    releaseDate = it.release_date ?: it.first_air_date ?: "",
                    type = "movie",
                    isMovie = true
                )
            }
        }
    }

    @Before
    fun setUp() {
        api = mock(WatchmodeApi::class.java)
        repository = WatchRepositoryImpl(api, fakeMapper)
    }

    @Test
    fun `getMoviesAndTvShows should return mapped movie and tv lists`() {
        val movieResponse = WatchContentResponse("1", "Movie 1", "desc", null, null, "2023-01-01", null, 8.0, null, "movie", 100.0, "en")
        val tvResponse = WatchContentResponse("2", "TV Show 1", "desc", null, null, null, "2023-01-02", 7.5, null, "tv", 90.0, "en")

        val movieListResponse = WatchContentListResponse("1", listOf(movieResponse), 1, 1)
        val tvListResponse = WatchContentListResponse("1", listOf(tvResponse), 1, 1)

        `when`(api.getMovies()).thenReturn(Single.just(movieListResponse))
        `when`(api.getTvShows()).thenReturn(Single.just(tvListResponse))

        val testObserver = repository.getMoviesAndTvShows().test()

        testObserver.assertComplete()
        val (movies, tvShows) = testObserver.values().first()
        assertEquals("Movie 1", movies.first().title)
        assertEquals("TV Show 1", tvShows.first().title)
    }
}
