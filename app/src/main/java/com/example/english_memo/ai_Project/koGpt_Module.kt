package com.example.english_memo.ai_Project

import com.example.english_memo.ai_Project.koGpt_Address.Companion.BASE_URL
import com.example.english_memo.BuildConfig.korest_Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class koGpt_Module {

    companion object {
        @Singleton
        @Provides
        fun getRetroInstance(): Retrofit {

            val client = OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun PostKogtp() : Kogpt_Dao {

            //return Cancer_Instance.api.getAlbums(perpage,per,current)
            return getRetroInstance().create(Kogpt_Dao::class.java)
        }

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