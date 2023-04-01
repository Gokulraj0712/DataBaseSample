package com.example.databasesample.database

import androidx.room.*

@Dao
interface NurseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nurseEntity: NurseEntity)

    @Query("SELECT * FROM NurseTable WHERE nurseId = :nurseId")
    suspend fun getNurseById(nurseId: Int): NurseEntity

    @Query("SELECT * FROM NurseTable WHERE nurseId = :nurseId AND password = :password")
    suspend fun login(nurseId: Int, password: String): NurseEntity

    @Delete
    suspend fun delete(nurseEntity: NurseEntity)

    @Query("DELETE FROM NurseTable")
    suspend fun deleteAll()


}