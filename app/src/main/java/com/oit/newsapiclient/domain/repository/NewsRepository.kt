package com.oit.newsapiclient.domain.repository

import com.oit.newsapiclient.data.model.Article
import com.oit.newsapiclient.data.model.NewsAPIResponse
import com.oit.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsHeadLines(): Resource<NewsAPIResponse>
    suspend fun getSearchedNews(searchQuery:String): Resource<NewsAPIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}