package com.example.databasesample.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.room.Room
import com.example.databasesample.R
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.databinding.ActivityMainBinding
import com.example.databasesample.repo.NurseRepository
import com.example.databasesample.repo.PatientRepository
import com.example.databasesample.repo.TestRepository
import com.example.databasesample.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var patientRepository: PatientRepository
    private lateinit var testRepository: TestRepository
    private lateinit var nurseRepository: NurseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val database = Room.databaseBuilder(applicationContext, HospitalDatabase::class.java, "HospitalDb").build()
        val patientDao = database.patientDao()
        patientRepository = PatientRepository(patientDao)

        val testDao=database.testDao()
        testRepository= TestRepository(testDao)

        val nurseDao=database.nurseDao()
        nurseRepository= NurseRepository(nurseDao)

        binding.startButton.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }



    }
}