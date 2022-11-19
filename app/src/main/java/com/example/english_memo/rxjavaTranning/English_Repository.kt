package com.example.english_memo.rxjavaTranning

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class English_Repository(application: Application) {

    private val english_dao : English_Dao
    private val english_list: Observable<List<English>>

    var database = English_Database.getInstance(application)

    init {
        var db = English_Database.getInstance(application)
        english_dao = db!!.EnglishDao()
        english_list = db.EnglishDao().getAll()
    }

    fun insert(English : English) : Completable {
        return english_dao.insert(English)
    }

    fun delete(English : English) : Completable {
        return english_dao.delete(English)
    }



    fun getAll(): Observable<List<English>> {

        /*
        db.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

         */

        return english_dao.getAll()
    }


    fun readDateData(year : Int, month : Int, day : Int): Observable<List<English>> {
        return english_dao.readAllData(year, month, day)
    }

    fun searchDatabase(searchQuery: String): Observable<List<English>> {
        return english_dao.searchDatabase(searchQuery)
    }


    /*
fun updateMemo(English : English){
    english_dao.update(English)
}

 */
}