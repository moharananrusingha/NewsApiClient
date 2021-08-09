package com.oit.newsapiclient.presentation.di.core

import com.oit.newsapiclient.domain.repository.NewsRepository
import com.oit.newsapiclient.domain.usecase.GetNewsHeadlineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadlineUseCase(
        newsRepository: NewsRepository
    ): GetNewsHeadlineUseCase =
        GetNewsHeadlineUseCase(newsRepository)
}