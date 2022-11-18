package com.example.english_memo.rxjavaTranning

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface English_Dao {
    @Query("SELECT * FROM RxKotlin_English ORDER BY id DESC")
    //fun getAll(): LiveData<List<English>>
    fun getAll(): Observable<List<English>>

    @Insert
    fun insert(English: English) : Completable

    /*
    @Update
    fun update(English: English)
     */

    @Delete
    fun delete(English: English) : Completable


    @Query("SELECT * FROM RxKotlin_English WHERE title LIKE :searchQuery ORDER BY id DESC")
    fun searchDatabase(searchQuery : String) : Single<List<English>>


    @Query("SELECT * FROM RxKotlin_English WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC")
    fun readAllData(year : Int, month : Int, day : Int) : Single<List<English>>


}