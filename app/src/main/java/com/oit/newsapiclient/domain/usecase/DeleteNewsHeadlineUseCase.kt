package com.oit.newsapiclient.domain.usecase

import com.oit.newsapiclient.data.model.Article
import com.oit.newsapiclient.domain.repository.NewsRepository

class DeleteNewsHeadlineUseCase (private val repository: NewsRepository) {
    suspend fun execute(article: Article) = repository.deleteNews(article)
}