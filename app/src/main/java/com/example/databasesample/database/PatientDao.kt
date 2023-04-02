package com.example.databasesample.database

import android.provider.Contacts.Intents.UI
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patientEntity: PatientEntitiy)

    @Insert
    suspend fun upsertAll(patientEntities: List<PatientEntitiy>)

    @Query("""
       SELECT * FROM PatientTable
    """)
    fun getPatientList(): LiveData<List<PatientEntitiy>>


    @Query("""
        SELECT * FROM PatientTable
        WHERE patientId LIKE :patid LIMIT 1
    """)
    fun getPatientById(patid: Int):PatientEntitiy

    @Query("""
        UPDATE PatientTable
        SET firstname = :firstname, lastname = :lastname, room = :room, department = :department
        WHERE patientId = :patientId;
    """)
    suspend fun updatePatientEntity(patientId: Int, firstname: String, lastname: String, room: String, department: String): Int


    @Query("""
        DELETE FROM PatientTable
        WHERE patientId = :patientId
    """)
    suspend fun deletePatientEntity(patientId: Int):Int

    @Query("SELECT * FROM PatientTable WHERE nurseId = :nurseId")
    suspend fun getPatientsForNurse(nurseId: Int): List<PatientEntitiy>


}