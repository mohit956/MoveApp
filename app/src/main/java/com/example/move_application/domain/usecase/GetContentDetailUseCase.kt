package com.example.move_application.domain.usecase

import com.example.move_application.data.model.WatchContent
import com.example.move_application.data.repository.WatchRepository


    class GetContentDetailUseCase(private val repository: WatchRepository) {
        suspend operator fun invoke(contentId: String, isMovie: Boolean): WatchContent {
            return repository.getContentDetails(contentId, isMovie)
        }
    }
