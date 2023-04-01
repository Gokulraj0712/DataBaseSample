package com.example.databasesample.repo


import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.NurseDao
import com.example.databasesample.database.NurseEntity



class NurseRepository (private val nurseDao: NurseDao) {


    suspend fun insertNurse2(nurseId: String, firstname: String, lastname: String, department: String, password: String) {
        val nurse = NurseEntity(nurseId, firstname, lastname, department, password)
        nurseDao.insert(nurse)
    }
    suspend fun insertNurse(nurseEntity: NurseEntity) {
        nurseDao.insert(nurseEntity)
    }

    suspend fun getNurseById(nurseId: String): NurseEntity {
        return nurseDao.getNurseById(nurseId.toInt())
    }

    suspend fun login(nurseId: String, password: String): NurseEntity {
        return nurseDao.login(nurseId.toInt(), password)
    }

}