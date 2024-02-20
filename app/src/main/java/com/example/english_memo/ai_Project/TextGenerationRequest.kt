package com.example.english_memo.ai_Project

import com.google.gson.annotations.SerializedName

data class TextGenerationRequest(
    val prompt: String,
    @SerializedName("max_tokens") val maxTokens: Int,
    val temperature: Double? = null,
    @SerializedName("top_p") val topP: Double? = null,
    val n: Int? = null
)