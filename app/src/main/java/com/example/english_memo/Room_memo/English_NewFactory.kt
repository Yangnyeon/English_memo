package com.example.english_memo.Room_memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.english_memo.rxjavaTranning.English_ViewModel

class English_NewFactory (private val repository : English_Repository) : ViewModelProvider.Factory {

/*    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }*/
}