package com.oit.newsapiclient.domain.usecase

import com.oit.newsapiclient.data.model.NewsAPIResponse
import com.oit.newsapiclient.data.util.Resource
import com.oit.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(
    private val repository: NewsRepository
) {
    suspend fun execute(searchQuery: String): Resource<NewsAPIResponse> =
        repository.getSearchedNews(searchQuery)
}