package com.example.databasesample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NurseDao {

    @Insert
    suspend fun insert(nurseEntity: NurseEntity)

    @Query("SELECT * FROM NurseTable WHERE nurseId = :nurseId")
    suspend fun getNurseById(nurseId: Int): NurseEntity

    @Query("SELECT * FROM NurseTable WHERE nurseId = :nurseId AND password = :password")
    suspend fun login(nurseId: Int, password: String): NurseEntity



}