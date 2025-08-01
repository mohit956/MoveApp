package com.example.move_application.domain.usecase

import com.example.move_application.data.model.WatchContent
import com.example.move_application.data.repository.WatchRepository


//    open class GetContentDetailUseCase(private val repository: WatchRepository) {
//        suspend operator fun invoke(contentId: String, isMovie: Boolean): WatchContent {
//            return repository.getContentDetails(contentId, isMovie)
//        }
//    }

open class GetContentDetailUseCase(
    private val repository: WatchRepository?
) {
    open suspend operator fun invoke(contentId: String, isMovie: Boolean): WatchContent {
        return repository!!.getContentDetails(contentId, isMovie)
    }
}
