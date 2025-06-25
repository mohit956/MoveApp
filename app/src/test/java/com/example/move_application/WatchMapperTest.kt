package com.example.move_application

import com.example.move_application.data.mapper.WatchMapper
import com.example.move_application.data.model.response.WatchContentResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class WatchMapperTest {

    private val mapper = WatchMapper()

    @Test
    fun `mapResponseToWatchContent should map movie response correctly`() {
        // Given
        val response = WatchContentResponse(
            id = "101",
            title = "Sample Movie",
            overview = "A movie about testing.",
            poster_path = "/poster.jpg",
            backdrop_path = "/backdrop.jpg",
            release_date = "2023-01-01",
            first_air_date = null,
            vote_average = 8.5,
            genre_ids = listOf(1, 2),
            media_type = "movie",
            popularity = 100.0,
            original_language = "en"
        )

        // When
        val result = mapper.mapResponseToWatchContent(response)

        // Then
        assertEquals("101", result.id)
        assertEquals("Sample Movie", result.title)
        assertEquals("A movie about testing.", result.description)
        assertEquals("https://image.tmdb.org/t/p/w500/poster.jpg", result.posterUrl)
        assertEquals("2023-01-01", result.releaseDate)
        assertEquals("movie", result.type)
        assertEquals(true, result.isMovie)
    }
}
