package com.oit.newsapiclient.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.oit.newsapiclient.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
class NewsAPIServiceTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var newsAPIService: NewsAPIService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        newsAPIService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NewsAPIService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getTopHeadlines_sendRequest_receivedExpected() {
        runBlocking {
            enqueMockResponse("json/NewsAPIResponse.json")
            val responseBody = newsAPIService.getTopHeadlines("us", 1).body()
            val request = mockWebServer.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=68dd80ee443b4760991cfee69b7cede1")
        }
    }

    @Test
    fun getTopHeadlines_receiveRequest_correctPageSize() {
        runBlocking {
            enqueMockResponse("json/NewsAPIResponse.json")
            val responseBody = newsAPIService.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            assertThat(articleList.size).isEqualTo(20)

        }
    }

    @Test
    fun getTopHeadlines_receiveRequest_correctContent() {
        runBlocking {
            enqueMockResponse("json/NewsAPIResponse.json")
            val responseBody = newsAPIService.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            val firstArticle = articleList[0]
            assertThat(firstArticle.author).isEqualTo("Julius Young")
            assertThat(firstArticle.url).isEqualTo("https://www.foxnews.com/entertainment/britney-spears-answers-fan-questions-conservatorship")
            assertThat(firstArticle.publishedAt).isEqualTo("2021-08-06T01:51:29Z")
        }
    }

    private fun enqueMockResponse(
        fileName:String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse:MockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }
}