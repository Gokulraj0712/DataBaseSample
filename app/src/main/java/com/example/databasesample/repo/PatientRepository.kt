package com.example.databasesample.repo


import com.example.databasesample.database.PatientDao
import com.example.databasesample.database.PatientEntitiy

class PatientRepository(private val patientDao: PatientDao) {


    suspend fun insertPatient(patientEntity: PatientEntitiy) {
        patientDao.upsert(patientEntity)
    }

    suspend fun getAllPatients(): List<PatientEntitiy> {
        return patientDao.getPatientList()
    }

    suspend fun getPatientById(patientId: String): PatientEntitiy {
        return patientDao.getPatientById(patientId)
    }

    suspend fun updatePatient(patientEntity: PatientEntitiy) {
        patientDao.upsert(patientEntity)
    }

    suspend fun deletePatientById(patientId: String) {
        patientDao.deletePatientEntity(patientId)
    }
}