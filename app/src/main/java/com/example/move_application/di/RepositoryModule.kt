package com.example.move_application.di

import com.example.move_application.data.mapper.WatchMapper
import com.example.move_application.data.repository.WatchRepository
import com.example.move_application.data.repository.WatchRepositoryImpl
import org.koin.dsl.module
import kotlin.math.sin

val repositoryModule = module {
    single { WatchMapper() }
    single<WatchRepository> { WatchRepositoryImpl(get(), get()) }
}
