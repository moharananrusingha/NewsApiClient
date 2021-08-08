package com.oit.newsapiclient.domain.usecase

import com.oit.newsapiclient.data.model.Article
import com.oit.newsapiclient.data.model.NewsAPIResponse
import com.oit.newsapiclient.data.util.Resource
import com.oit.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlineUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<NewsAPIResponse> =
        newsRepository.getNewsHeadLines(country, page)
}