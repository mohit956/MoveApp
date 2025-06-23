package com.example.move_application.presentation.home

import com.example.move_application.data.model.WatchContent


sealed class HomeState {
    object Loading : HomeState()
    data class Success(
        val movies: List<WatchContent>,
        val tvShows: List<WatchContent>
    ) : HomeState()
    data class Error(val message: String) : HomeState()
}