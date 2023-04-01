package com.example.databasesample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PatientEntitiy::class, NurseEntity::class,TestEntitiy::class], version = 1)
abstract class HospitalDatabase:RoomDatabase() {
    abstract fun patientDao():PatientDao
    abstract fun nurseDao():NurseDao
    abstract fun testDao():TestDao

    companion object {
        @Volatile
        private var INSTANCE: HospitalDatabase? = null

        fun getDatabase(context: Context): HospitalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HospitalDatabase::class.java,
                    "hospital_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }



}