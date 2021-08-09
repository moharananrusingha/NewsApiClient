package com.oit.newsapiclient.presentation.di.core

import android.app.Application
import com.oit.newsapiclient.domain.usecase.GetNewsHeadlineUseCase
import com.oit.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        app: Application,
        getNewsHeadlineUseCase: GetNewsHeadlineUseCase
    ): NewsViewModelFactory = NewsViewModelFactory(app, getNewsHeadlineUseCase)
}