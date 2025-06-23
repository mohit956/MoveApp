package com.example.move_application.presentation.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.move_application.data.model.WatchContent
import com.example.move_application.presentation.home.components.ContentGrid
import com.example.move_application.presentation.home.components.ErrorContent
import com.example.move_application.presentation.home.components.HomeTopBar
import com.example.move_application.presentation.home.components.ShimmerEffect
import com.example.move_application.utils.ContentType
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToDetails: (String) -> Unit,
) {
    val state: HomeState by viewModel.state.collectAsState()
    val contentType: ContentType by viewModel.contentType.collectAsState()

    Column {
        HomeTopBar(
            selectedType = contentType,
            onTypeSelected = viewModel::setContentType
        )

        AnimatedContent(targetState = state) {
                currentState ->
            when(currentState) {
                is HomeState.Loading -> ShimmerEffect()
                is HomeState.Success -> {
                    val content: List<WatchContent> = when(contentType) {
                        ContentType.MOVIES -> currentState.movies
                        ContentType.TV_SHOWS -> currentState.tvShows
                    }

                    ContentGrid(
                        contents = content,
                        onItemClick = onNavigateToDetails,
                    )
                }
                is HomeState.Error -> {
                    ErrorContent(
                        message = currentState.message,
                        onRetry = viewModel::loadContent,
                    )
                }
            }
        }
    }
}
