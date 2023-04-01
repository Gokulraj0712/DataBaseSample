package com.example.databasesample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "TestTable")
data class TestEntitiy (

        @PrimaryKey(autoGenerate = true) val testId : Int?,
        @ColumnInfo(name = "BPL") val bpl:Double?,
        @ColumnInfo(name = "BPH") val bph:Double?,
        @ColumnInfo(name = "temperature") val temperature:Double?,
        @ColumnInfo(name = "nurseId") val nurseId:Int?,
        @ColumnInfo(name = "patientId") val patientId:Int?
        )