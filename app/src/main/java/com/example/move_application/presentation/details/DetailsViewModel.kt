package com.example.move_application.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.move_application.data.model.WatchContent
import com.example.move_application.domain.usecase.GetContentDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getContentDetailsUseCase: GetContentDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    fun loadContent(contentId: String, isMovie: Boolean) {
        _state.value = DetailsState.Loading

        viewModelScope.launch {
            try {
                val content: WatchContent = getContentDetailsUseCase(contentId, isMovie)
                _state.value = DetailsState.Success(content)
            } catch (e: Exception) {
                _state.value = DetailsState.Error("Failed to load content")
            }
        }
    }
}
