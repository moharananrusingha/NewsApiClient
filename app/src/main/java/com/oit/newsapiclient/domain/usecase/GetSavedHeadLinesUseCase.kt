package com.oit.newsapiclient.domain.usecase

import com.oit.newsapiclient.data.model.Article
import com.oit.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedHeadLinesUseCase (private val repository: NewsRepository) {
    fun execute(): Flow<List<Article>> = repository.getSavedNews()
}