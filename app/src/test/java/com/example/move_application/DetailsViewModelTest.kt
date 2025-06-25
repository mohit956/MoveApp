package com.example.move_application

import com.example.move_application.data.model.WatchContent
import com.example.move_application.domain.usecase.GetContentDetailUseCase
import com.example.move_application.presentation.details.DetailsState
import com.example.move_application.presentation.details.DetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel
    private val testDispatcher = StandardTestDispatcher()

    // Fake use case that returns mock WatchContent
    private val fakeUseCase = object : GetContentDetailUseCase(null) {
        override suspend fun invoke(contentId: String, isMovie: Boolean): WatchContent {
            return WatchContent(
                id = contentId,
                title = "Fake Movie",
                posterUrl = "/fake.jpg",
                description = "This is a fake description.",
                releaseDate = "2025-01-01",
                type = "movie",
                isMovie = true
            )
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(fakeUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadContent should emit Success state`() = runTest {
        viewModel.loadContent("1", isMovie = true)
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.state.value
        assert(result is DetailsState.Success)
        assertEquals("Fake Movie", (result as DetailsState.Success).content.title)
    }
}
