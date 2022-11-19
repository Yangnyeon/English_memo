package com.example.english_memo.rxjavaTranning

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


import io.reactivex.schedulers.Schedulers


import retrofit2.Response
import java.util.*

class English_ViewModel(private val repository : English_Repository) : AndroidViewModel(Application()) {

    private val items = repository.getAll()

    var _currentData = MutableLiveData<List<English>>()

    val successEvent: SingleLiveEvent<Any> = SingleLiveEvent()


    init {

    }


    /*
    val currentData : LiveData<List<English>>
        get() = _currentData

     */


    fun insert(English : English) {

        repository.insert(English)
    }

    fun insertUser(English: English) {


        repository.insert(English)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()


        /*
        CompositeDisposable().add(repository.insert(English)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe(
                { successEvent.call()
                },
                { error -> // error event
                 })
        )
        */

        /*

        appDataBase.RoomDao().insertMsg(new DbTable(editText.getText().toString()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe();

         */

        /*
        CompositeDisposable().add(repository.readDateData(English)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe()

         */

    }

    fun delete(English : English){
        /*
        repository.delete(English)

         */

        repository.delete(English)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe()
    }

    /*
    fun getAll() : MutableLiveData<List<English>>{

        repository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe()

        return _currentData

    }

     */


    fun getAll() : MutableLiveData<List<English>> {

        repository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                _currentData.value = it
            }
            .addTo(compositeDisposable = CompositeDisposable())

        return _currentData
    }



    /*
    fun updateMemo(English : English){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemo(cigarette)
        }
    }

     */






    fun readDateData(year : Int, month : Int, day : Int) : MutableLiveData<List<English>> {

        repository.readDateData(year, month, day)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe{
                _currentData.value = it
            }
            .addTo(compositeDisposable = CompositeDisposable())

        return _currentData


    }





    fun searchDatabase(searchQuery: String) : MutableLiveData<List<English>>{

        repository.searchDatabase(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe{
                _currentData.value = it
            }
            .addTo(compositeDisposable = CompositeDisposable())

        return _currentData

    }





}