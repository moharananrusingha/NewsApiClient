package com.oit.newsapiclient.presentation.di.core

import com.oit.newsapiclient.data.api.NewsAPIService
import com.oit.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.oit.newsapiclient.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsAPIService: NewsAPIService
    ): NewsRemoteDataSource =
        NewsRemoteDataSourceImpl(newsAPIService)
}