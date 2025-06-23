package com.example.move_application.presentation.details

import com.example.move_application.data.model.WatchContent

sealed class DetailsState {
    data object Loading : DetailsState()
    data class Success(val content: WatchContent) : DetailsState()
    data class Error(val message: String) : DetailsState()
}