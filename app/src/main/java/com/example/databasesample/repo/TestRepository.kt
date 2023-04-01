package com.example.databasesample.repo

import com.example.databasesample.database.TestDao
import com.example.databasesample.database.TestEntitiy

class TestRepository(private val testDao: TestDao) {


    suspend fun insertTest(testEntity: TestEntitiy) {
        testDao.upsert(testEntity)
    }

    suspend fun getTestsByPatientId(patientId: String): List<TestEntitiy> {
        return testDao.getTestByPatientId(patientId)
    }

    suspend fun updateTest(testEntity: TestEntitiy) {
        testDao.update(testEntity)
    }

    suspend fun deleteTest(testEntity: TestEntitiy) {
        testDao.delete(testEntity)
    }
}