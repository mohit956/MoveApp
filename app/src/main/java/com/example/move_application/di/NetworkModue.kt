package com.example.move_application.di


import com.example.move_application.data.api.WatchmodeApi
import com.facebook.shimmer.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule= module {
    single {
        provideAuthInterceptor()
    }

    single {
        provideOkHttpClient(get())
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(WatchmodeApi::class.java)
    }
}

private fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
    val original = chain.request()
    val originalUrl = original.url

    val url = originalUrl.newBuilder()
        .addQueryParameter("api_key", "388a87b81ac04d21c0c61c6f9030f493")
        .build()
    val request = original.newBuilder()
        .url(url)
        .build()

    chain.proceed(request)
}

private fun provideOkHttpClient(authInterceptor: Interceptor) = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    })
    .connectTimeout(15, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .build()

