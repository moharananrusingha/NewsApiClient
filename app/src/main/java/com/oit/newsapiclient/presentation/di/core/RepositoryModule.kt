package com.oit.newsapiclient.presentation.di.core

import com.oit.newsapiclient.data.repository.NewsRepositoryImpl
import com.oit.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.oit.newsapiclient.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import com.oit.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository =
        NewsRepositoryImpl(newsRemoteDataSource)


}