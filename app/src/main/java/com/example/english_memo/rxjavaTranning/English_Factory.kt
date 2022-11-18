package com.example.english_memo.rxjavaTranning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class English_Factory(private val repository : English_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return English_ViewModel(repository) as T
    }
}