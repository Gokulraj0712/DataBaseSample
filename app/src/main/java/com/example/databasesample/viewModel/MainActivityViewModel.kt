package com.example.databasesample.viewModel

import androidx.lifecycle.ViewModel
import com.example.databasesample.database.NurseEntity
import com.example.databasesample.database.PatientEntitiy
import com.example.databasesample.database.TestEntitiy
import com.example.databasesample.repo.NurseRepository
import com.example.databasesample.repo.PatientRepository
import com.example.databasesample.repo.TestRepository

class MainActivityViewModel:ViewModel() {

    private var patientRepository: PatientRepository? = null
    private var nurseRepository:NurseRepository?=null
    private var testRepository: TestRepository? = null

    fun setupPatientRepository(patientRepository: PatientRepository) {
         this.patientRepository = patientRepository
    }
    fun setupNurseRepository(nurseRepository: NurseRepository) {
        this.nurseRepository = nurseRepository
    }

    fun setupTestRepository(testRepository: TestRepository) {
        this.testRepository = testRepository
    }


//PatientRepository Methods
    suspend fun insertPatient(patientEntity: PatientEntitiy) {
        patientRepository?.insertPatient(patientEntity)
    }

    suspend fun getPatientById(patientId: String): PatientEntitiy? {
        return patientRepository?.getPatientById(patientId)
    }

    suspend fun getAllPatients(): List<PatientEntitiy> {
        return patientRepository?.getAllPatients() ?: emptyList()
    }

    suspend fun updatePatient(patientEntity: PatientEntitiy) {
        patientRepository?.updatePatient(patientEntity)
    }

    suspend fun deletePatientById(patientId: String) {
        patientRepository?.deletePatientById(patientId)
    }

    // NurseRepository methods
    suspend fun insertNurse2(nurseEntity: NurseEntity) {
        nurseRepository?.insertNurse(nurseEntity)
    }

    suspend fun insertNurse(nurseEntity: NurseEntity) {
        nurseRepository?.insertNurse(nurseEntity)
    }

    suspend fun getNurseById(nurseId: String): NurseEntity? {
        return nurseRepository?.getNurseById(nurseId)
    }

    suspend fun login(nurseId: String, password: String): NurseEntity? {
        return nurseRepository?.login(nurseId, password)
    }

    // TestRepository methods
    suspend fun insertTest(testEntity: TestEntitiy) {
        testRepository?.insertTest(testEntity)
    }

    suspend fun getTestsByPatientId(patientId: String): List<TestEntitiy> {
        return testRepository?.getTestsByPatientId(patientId) ?: emptyList()
    }

    suspend fun updateTest(testEntity: TestEntitiy) {
        testRepository?.updateTest(testEntity)
    }

    suspend fun deleteTest(testEntity: TestEntitiy) {
        testRepository?.deleteTest(testEntity)
    }

}