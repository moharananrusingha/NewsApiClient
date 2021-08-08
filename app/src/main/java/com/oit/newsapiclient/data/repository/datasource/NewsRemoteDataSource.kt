package com.oit.newsapiclient.data.repository.datasource

import com.oit.newsapiclient.data.model.NewsAPIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<NewsAPIResponse>
}