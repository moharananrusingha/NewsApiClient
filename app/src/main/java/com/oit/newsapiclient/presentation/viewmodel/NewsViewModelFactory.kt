package com.oit.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oit.newsapiclient.domain.usecase.GetNewsHeadlineUseCase
import java.lang.IllegalArgumentException

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadlineUseCase: GetNewsHeadlineUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(app, getNewsHeadlineUseCase) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }
}