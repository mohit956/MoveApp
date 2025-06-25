
package com.example.move_application

import android.app.Application
import com.example.move_application.di.appModule
import com.example.move_application.di.networkModule
import com.example.move_application.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WatchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin with context and modules
        startKoin {
            androidLogger(Level.ERROR) // You can use Level.INFO or Level.DEBUG for development
            androidContext(this@WatchApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}



//import android.app.Application
//import com.example.move_application.di.appModule
//import com.example.move_application.di.networkModule
//import com.example.move_application.di.repositoryModule
//import org.koin.android.ext.koin.androidContext
//import org.koin.core.context.GlobalContext.startKoin
//
//class WatchApplication : Application() {
//    override fun onCreate() {
//        super.onCreate()
//        startKoin {
//            androidContext(this@WatchApplication)
//            modules(appModule, networkModule, repositoryModule)
//        }
//    }
//}
