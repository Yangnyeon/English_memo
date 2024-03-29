package com.example.english_memo.Room_memo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
    var allUsers : MutableLiveData<ArrayList<UserEntity>>

    private val repository = English_Repository(app)


    init{
        allUsers = MutableLiveData()
        getAllUsers()
    }


    fun getAllUsersObservers(): MutableLiveData<ArrayList<UserEntity>> {
        return allUsers
    }

    fun getAllUsers() {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        val list = userDao?.getAllUserInfo()

        allUsers.postValue(list as ArrayList<UserEntity>?)
    }

    fun insertUserInfo(entity: UserEntity){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.insertUser(entity)
        getAllUsers()
    }

    /*
    fun updateUserInfo(entity: UserEntity){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.updateUser(entity)
        getAllUsers()
    }

     */

    fun deleteUserInfo(entity: UserEntity){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.deleteUser(entity)
        getAllUsers()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<UserEntity>>? {
/*        return repository.searchDatabase(searchQuery).asLiveData()*/
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        return userDao?.searchDatabase(searchQuery)
    }
}
