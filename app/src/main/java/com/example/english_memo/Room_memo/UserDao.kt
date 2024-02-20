package com.example.english_memo.Room_memo

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {


    @Query("SELECT * FROM English_memo ORDER BY id DESC")
    fun getAllUserInfo(): List<UserEntity>

    @Insert
    fun insertUser(user: UserEntity?)

    @Delete
    fun deleteUser(user: UserEntity?)

    @Update
    fun updateUser(user: UserEntity?)

    @Query("SELECT * FROM English_memo")
    fun getUserAll(): List<UserEntity>?

    @Query("SELECT * FROM English_memo WHERE contents LIKE :searchQuery ORDER BY id DESC")
    fun searchDatabase(searchQuery : String) : LiveData<List<UserEntity>>

}