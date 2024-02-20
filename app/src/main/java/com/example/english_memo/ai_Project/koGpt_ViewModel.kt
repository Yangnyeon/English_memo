package com.example.english_memo.ai_Project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.english_memo.rxjavaTranning.SingleLiveEvent
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class koGpt_ViewModel @Inject constructor(private val repository : koGpt_Repository) : ViewModel() {

  val myResponse : MutableLiveData<Response<koGpt_Data>> = SingleLiveEvent()


    fun PostKoGpt(prompt : String) {
        viewModelScope.launch {
            val post_Response = repository.generateKoGPT(prompt)
            myResponse.value = post_Response
        }
    }

}