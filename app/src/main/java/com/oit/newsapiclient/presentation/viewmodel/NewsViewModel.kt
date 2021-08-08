package com.oit.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

import com.oit.newsapiclient.data.model.NewsAPIResponse
import com.oit.newsapiclient.data.util.Resource
import com.oit.newsapiclient.domain.usecase.GetNewsHeadlineUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsViewModel (
    private val app: Application,
    private val getNewsHeadlineUseCase: GetNewsHeadlineUseCase
        ) : AndroidViewModel(app) {

    val newsHeadLine:MutableLiveData<Resource<NewsAPIResponse>> = MutableLiveData()

    fun getNewsHeadLine(country:String, page:Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLine.postValue(Resource.Loading())
        try {
            if(isNetConnectionAvailable(app)){
                val apiResult = getNewsHeadlineUseCase.execute(country, page)
                newsHeadLine.postValue(apiResult)
            } else {
                newsHeadLine.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e:Exception) {
            newsHeadLine.postValue(Resource.Error("Error: ${e.message}"))
        }


    }

    private fun isNetConnectionAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            @Suppress("DEPRECATION")
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            @Suppress("DEPRECATION")
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}