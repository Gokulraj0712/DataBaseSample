package com.example.databasesample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "TestTable")
data class TestEntitiy (

        @PrimaryKey val testId : String,
        @ColumnInfo(name = "BPL") val bpl:Double,
        @ColumnInfo(name = "BPH") val bph:Double,
        @ColumnInfo(name = "temperature") val temperature:Double,
        @ColumnInfo(name = "nurseId") val nurseId:String,
        @ColumnInfo(name = "patientId") val patientId:String
        )