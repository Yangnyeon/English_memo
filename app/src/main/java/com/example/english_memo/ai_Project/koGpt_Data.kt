package com.example.english_memo.ai_Project

import com.google.gson.annotations.SerializedName

class koGpt_Data (
    val id: String,
    val generations: ArrayList<Generation>,
    val Usage: Usage
)

data class Generation(
    val text: String,
    val tokens: Int
)

data class Usage(
    @SerializedName("prompt_tokens") val promptTokens: Int,
    @SerializedName("generated_tokens") val generatedTokens: Int,
    @SerializedName("total_tokens") val totalTokens: Int
)