package com.example.databasesample.database

import androidx.room.*

@Dao
interface TestDao {

    @Insert
    suspend fun upsert(testEntity: TestEntitiy)

    @Query("""
        SELECT * FROM TestTable
        WHERE patientId = :patientId
    """)
    suspend fun getTestByPatientId(patientId: String): List<TestEntitiy>

    @Update
    suspend fun update(testEntity: TestEntitiy  )

    @Delete
    suspend fun delete(testEntity: TestEntitiy)

}