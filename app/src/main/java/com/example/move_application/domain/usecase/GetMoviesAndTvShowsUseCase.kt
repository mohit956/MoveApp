package com.example.move_application.domain.usecase
import com.example.move_application.data.model.WatchContent
import com.example.move_application.data.repository.WatchRepository
import io.reactivex.rxjava3.core.Single


    class GetMoviesAndTvShowsUseCase(private val repository: WatchRepository) {

        operator fun invoke(): Single<Pair<List<WatchContent>, List<WatchContent>>> {
            return repository.getMoviesAndTvShows()
        }
    }

