package com.example.move_application

import com.example.move_application.data.model.WatchContent
import com.example.move_application.data.repository.WatchRepository
import com.example.move_application.domain.usecase.GetContentDetailUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.junit.Assert.assertEquals

class GetContentDetailUseCaseTest {

    private lateinit var repository: WatchRepository
    private lateinit var useCase: GetContentDetailUseCase

    @Before
    fun setUp() {
        repository = mock()
        useCase = GetContentDetailUseCase(repository)
    }

    @Test
    fun `invoke should return WatchContent when repository returns data`() {
        runBlocking {
            // Given
            val contentId = "123"
            val isMovie = true
            val expectedContent = WatchContent(
                id = "123",
                title = "Test Title",
                posterUrl = "/poster.jpg",
                description = "Test description",
                releaseDate = "2023-01-01",
                type = "movie",
                isMovie = true
            )

            `when`(repository.getContentDetails(contentId, isMovie)).thenReturn(expectedContent)

            // When
            val result = useCase.invoke(contentId, isMovie)

            // Then
            assertEquals(expectedContent, result)
            verify(repository).getContentDetails(contentId, isMovie)
        }
    }

}
