package com.example.english_memo.ai_Project

import com.example.english_memo.BuildConfig.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Kogpt_Dao {

    @POST("/v1/inference/kogpt/generation")
    suspend fun generateKoGPT ( @Body koGPTRequest: TextGenerationRequest)
            : Response<koGpt_Data>

}