package com.example.newsapp.data.di

import com.example.newsapp.data.remote.TMDBService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieNetworkModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9..."

    @Provides
    @Singleton
    @Named("MovieAuthInterceptor")
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", BEARER_TOKEN)
            .addHeader("Accept", "application/json")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @Named("MovieOkHttpClient")
    fun provideOkHttpClient(@Named("MovieAuthInterceptor") authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    @Named("MovieRetrofit")
    fun provideRetrofit(@Named("MovieOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTMDBService(@Named("MovieRetrofit") retrofit: Retrofit): TMDBService {
        return retrofit.create(TMDBService::class.java)
    }
}
