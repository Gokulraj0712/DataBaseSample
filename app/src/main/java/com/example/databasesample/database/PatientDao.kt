package com.example.databasesample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(patientEntity: PatientEntitiy)

    @Insert
    suspend fun upsertAll(patientEntities: List<PatientEntitiy>)

    @Query("""
       SELECT * FROM PatientTable
    """)
    suspend fun getPatientList(): List<PatientEntitiy>


    @Query("""
        SELECT * FROM PatientTable
        WHERE patientId = :patientId
    """)
    suspend fun getPatientById(patientId: String): PatientEntitiy

    @Query("""
        UPDATE PatientTable
        SET firstname = :firstname, lastname = :lastname, room = :room, department = :department
        WHERE patientId = :patientId;
    """)
    suspend fun updatePatientEntity(patientId: String, firstname: String, lastname: String, room: String, department: String)

    @Query("""
        DELETE FROM PatientTable
        WHERE patientId = :patientId
    """)
    suspend fun deletePatientEntity(patientId: String)


}