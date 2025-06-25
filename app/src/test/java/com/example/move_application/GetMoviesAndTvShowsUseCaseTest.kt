package com.example.move_application

import com.example.move_application.data.model.WatchContent
import com.example.move_application.data.repository.WatchRepository
import com.example.move_application.domain.usecase.GetMoviesAndTvShowsUseCase
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mockito.*
import org.junit.Assert.assertEquals

class GetMoviesAndTvShowsUseCaseTest {

    private val repository = mock(WatchRepository::class.java)
    private val useCase = GetMoviesAndTvShowsUseCase(repository)

    @Test
    fun `invoke should return movie and tv show lists`() {
        // Given
        val movies = listOf(
            WatchContent("1", "Movie 1", "/poster1", "desc", "2024-01-01", "movie", true)
        )
        val tvShows = listOf(
            WatchContent("2", "TV Show 1", "/poster2", "desc", "2024-02-02", "tv_show", false)
        )

        `when`(repository.getMoviesAndTvShows()).thenReturn(Single.just(Pair(movies, tvShows)))

        // When
        val testObserver = useCase.invoke().test()

        // Then
        testObserver.assertComplete()
        testObserver.assertValue { it.first == movies && it.second == tvShows }
    }

    @Test
    fun `invoke should emit error when repository fails`() {
        // Given
        val error = Throwable("API error")
        `when`(repository.getMoviesAndTvShows()).thenReturn(Single.error(error))

        // When
        val testObserver = useCase.invoke().test()

        // Then
        testObserver.assertError(error)
    }
}
