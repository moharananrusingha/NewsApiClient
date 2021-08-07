package com.oit.newsapiclient.data.repository

import com.oit.newsapiclient.data.model.Article
import com.oit.newsapiclient.data.model.NewsAPIResponse
import com.oit.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.oit.newsapiclient.data.util.Resource
import com.oit.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getNewsHeadLines(): Resource<NewsAPIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines())
    }

    private fun responseToResource(response:Response<NewsAPIResponse>):Resource<NewsAPIResponse> {
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }

        return Resource.Error(response.message())
    }

    override suspend fun getSearchedNews(searchQuery: String): Resource<NewsAPIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}