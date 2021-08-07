package com.oit.newsapiclient.domain.usecase

import com.oit.newsapiclient.data.model.Article
import com.oit.newsapiclient.domain.repository.NewsRepository

class SaveNewsHeadlineUseCase (private val repository: NewsRepository){
    suspend fun execute(article: Article) = repository.saveNews(article)
}