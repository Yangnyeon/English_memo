package com.example.english_memo.Room_memo

import androidx.room.*

@Dao
interface UserDao {


    @Query("SELECT * FROM English_memo ORDER BY id DESC")
    fun getAllUserInfo(): List<UserEntity>?

    @Insert
    fun insertUser(user: UserEntity?)

    @Delete
    fun deleteUser(user: UserEntity?)

    @Update
    fun updateUser(user: UserEntity?)

    @Query("SELECT * FROM English_memo")
    fun getUserAll(): List<UserEntity>?

}