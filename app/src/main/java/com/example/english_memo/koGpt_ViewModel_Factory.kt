package com.example.english_memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.english_memo.ai_Project.koGpt_Repository
import com.example.english_memo.ai_Project.koGpt_ViewModel

class koGpt_ViewModel_Factory (private val repository : koGpt_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return koGpt_ViewModel(repository) as T
    }
}