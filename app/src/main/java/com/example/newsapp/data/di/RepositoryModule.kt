package com.example.newsapp.data.di

import com.example.newsapp.data.repestory.NewsRepositoryImpl
import com.example.newsapp.data.repestory.TMDBRepositoryImpl
import com.example.newsapp.data.repestory.TMDBRepositoryImplUsingDOAPIRequest
import com.example.newsapp.data.repestory.UserRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.repository.TMDBRepository
import com.example.newsapp.domain.repository.TMDRepositoryUsingDOAPIRequest
import com.example.newsapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    @Singleton
    abstract fun bindTMDBRepository(
        tmdbRepository: TMDBRepositoryImpl
    ): TMDBRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository



    @Binds
    @Singleton
    abstract fun bindTMDRepositoryUsingDOAPIRequest(
        tmdRepositoryUsingDOAPIRequest: TMDBRepositoryImplUsingDOAPIRequest
    ): TMDRepositoryUsingDOAPIRequest
}
