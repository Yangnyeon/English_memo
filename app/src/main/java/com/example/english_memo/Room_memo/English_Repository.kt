package com.example.english_memo.Room_memo

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class English_Repository(application: Application) {

    private val UserDao: UserDao
    private val Englis_List: List<UserEntity>

    init {
        var db = RoomAppDb.getAppDatabase(application)
        UserDao = db!!.userDao()!!
        Englis_List = db.userDao()!!.getAllUserInfo()
    }



    fun searchDatabase(searchQuery: String): LiveData<List<UserEntity>> {
        return UserDao.searchDatabase(searchQuery)
    }


}