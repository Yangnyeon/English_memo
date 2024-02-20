package com.example.english_memo.ai_Project

import retrofit2.Response
import javax.inject.Inject

class koGpt_Repository  @Inject constructor() {


    private val Kogpt_Dao: Kogpt_Dao

    init {
        var db = koGpt_Module.PostKogtp()
        Kogpt_Dao = db!!
    }


    suspend fun generateKoGPT(prompt: String): Response<koGpt_Data> {
        return Kogpt_Dao.generateKoGPT(TextGenerationRequest(prompt = prompt,maxTokens = 64,temperature = 0.7 , topP = 0.8))
    }


}