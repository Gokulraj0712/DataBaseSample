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
    suspend fun getTestByPatientId(patientId: Int): List<TestEntitiy>



    @Query("""
        SELECT * FROM TestTable
        WHERE nurseId = :nurseId
    """)
    suspend fun getTestByNurseId(nurseId: Int): List<TestEntitiy>


    @Update
    suspend fun update(testEntity: TestEntitiy  )

    @Delete
    suspend fun delete(testEntity: TestEntitiy)

    @Query("""
    SELECT * FROM TestTable
    WHERE patientId = :patientId AND nurseId = :nurseId
""")
    suspend fun getTestsByNurseandPatient(nurseId: Int,patientId: Int) : List<TestEntitiy>

}