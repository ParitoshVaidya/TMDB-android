package com.supersnippets.tmdb.di

import com.supersnippets.tmdb.helpers.ApiService
import com.supersnippets.tmdb.helpers.AuthInterceptor
import com.supersnippets.tmdb.helpers.BASE_URL
import com.supersnippets.tmdb.helpers.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { NetworkInterceptor(androidContext()) }
    single { AuthInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideApi(get()) }
    single { provideRetrofit(get()) }
}


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    println("generated retrofit")
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(
    networkInterceptor: NetworkInterceptor,
    authInterceptor: AuthInterceptor
): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    println("generated OkHttpClient")
    return OkHttpClient()
        .newBuilder()
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(networkInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .build()
}

fun provideApi(retrofit: Retrofit): ApiService {
    println("generated ApiService")
    return retrofit.create(ApiService::class.java)
}
