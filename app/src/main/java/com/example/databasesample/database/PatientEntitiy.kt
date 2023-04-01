package com.example.databasesample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "PatientTable")
data class PatientEntitiy (

    @PrimaryKey(autoGenerate = true) val patientId : Int?,
    @ColumnInfo(name = "firstname") val firstname:String?,
    @ColumnInfo(name = "lastname") val lastname:String?,
    @ColumnInfo(name = "department") val department:String?,
    @ColumnInfo(name = "room") val room:String?,
    @ColumnInfo(name = "nurseId") val nurseId:Int?

        )
