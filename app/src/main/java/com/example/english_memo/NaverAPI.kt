package com.example.english_memo

import retrofit2.Call
import retrofit2.http.*

interface NaverAPI {
    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    fun getSearchNews(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String
    ): Call<ResultTransferPapago>
}