package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.databinding.ActivityPatientBinding
import kotlinx.coroutines.launch
import com.example.databasesample.adapters.PatientAdapter
import com.example.databasesample.database.PatientEntitiy

class PatientActivity: AppCompatActivity() {
    private lateinit var binding: ActivityPatientBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NURSE_ID = "pref_nurse_id"
    private lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val nurseId = sharedPreferences.getInt(PREF_NURSE_ID, -1)

        println(nurseId)

        // Initialize the patient adapter
        patientAdapter = PatientAdapter()

        // Set up the RecyclerView
        binding.rvPatients.apply {
            layoutManager = LinearLayoutManager(this@PatientActivity)
            adapter = patientAdapter

            // Add divider
            val dividerItemDecoration = DividerItemDecoration(this@PatientActivity, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }



        // Load patients from the database and display them in the RecyclerView
        loadPatients(nurseId)

        // Set up FAB click listener
        binding.fabAddPatient.setOnClickListener {
            // Create a new activity to collect information about the new patient
            val intent = Intent(this, AddPatientActivity::class.java)
            startActivity(intent)
        }


    }

    private fun loadPatients(nurseId: Int) {
        // Get the patient DAO from the database
        val patientDao = HospitalDatabase.getDatabase(this).patientDao()

        // Retrieve the list of patients for the specified nurse from the database
        lifecycleScope.launch {
            val patients = patientDao.getPatientsForNurse(nurseId)

            // Update the adapter with the new list of patients
            patientAdapter.setData(patients as List<PatientEntitiy>)
        }
    }

    override fun onBackPressed() {
        // Navigate to TestActivity screen
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}