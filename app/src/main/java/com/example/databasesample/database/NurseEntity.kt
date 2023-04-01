package com.example.databasesample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NurseTable")
data class NurseEntity (

    @PrimaryKey(autoGenerate = true) val nurseId : Int?,
    @ColumnInfo(name = "firstname") val firstname:String?,
    @ColumnInfo(name = "lastname") val lastname:String?,
    @ColumnInfo(name = "department") val department:String?,
    @ColumnInfo(name = "password") val password:String?

        )
