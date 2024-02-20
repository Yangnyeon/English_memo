package com.example.english_memo.ai_Project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.english_memo.BuildConfig.korest_Api
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.Response

class koGpt_ViewMOdel_Factory(private val repository : koGpt_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return koGpt_ViewModel(repository) as T
    }

    private object HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder().apply {
                addHeader("Content-Type", "application/json")
                addHeader("Authorization", "KakaoAK $korest_Api")
            }.build()
            return chain.proceed(request)
        }
    }
}