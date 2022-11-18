package com.example.english_memo.rxjavaTranning

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RxKotlin_English")
data class English (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    var title: String,
    var date : String,
    var Year : Int,
    var Month : Int,
    var Day : Int,
    var time : String,
)

