package com.example.move_application.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.move_application.WatchApplication
import com.example.move_application.domain.usecase.GetContentDetailUseCase
import com.example.move_application.domain.usecase.GetMoviesAndTvShowsUseCase
import com.example.move_application.presentation.home.HomeViewModel
import com.example.move_application.presentation.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {
    single { WatchApplication() }

    factory { GetContentDetailUseCase(get()) }
    factory { GetMoviesAndTvShowsUseCase(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}
