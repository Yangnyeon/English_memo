package com.example.english_memo.rxjavaTranning

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [English::class], version = 2, exportSchema = true)
abstract class English_Database : RoomDatabase()  {
    abstract fun EnglishDao(): English_Dao
//autoMigrations = [AutoMigration (from = 1, to = 2)]

    companion object {
        private var instance: English_Database? = null

        @Synchronized
        fun getInstance(context: Context): English_Database? {
            if (instance == null) {
                synchronized(English_Database::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        English_Database::class.java,
                        "RxKotlin_English"
                    ).build()
                }
            }
            return instance
        }
    }
}