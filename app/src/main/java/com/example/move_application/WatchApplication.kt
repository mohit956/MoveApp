package com.example.move_application

import android.app.Application
import com.example.move_application.di.appModule
import com.example.move_application.di.networkModule
import com.example.move_application.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WatchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WatchApplication)
            modules(appModule, networkModule, repositoryModule)
        }
    }
}
