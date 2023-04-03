package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasesample.adapters.TestAdapter
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.TestEntitiy
import com.example.databasesample.databinding.ActivityTestBinding
import kotlinx.coroutines.launch

class TestActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NURSE_ID = "pref_nurse_id"
    private val PREF_PATIENT_ID = "pref_patient_id"
    private lateinit var testAdapter: TestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val nurseId = sharedPreferences.getInt(PREF_NURSE_ID, -1)
        val patientId = sharedPreferences.getInt(PREF_PATIENT_ID, -1) // -1 is the default value if the key is not found


        // Initialize the patient adapter
        testAdapter = TestAdapter()

        // Set up the RecyclerView
        binding.rvTest.apply {
            layoutManager = LinearLayoutManager(this@TestActivity)
            adapter = testAdapter

            // Add divider
            val dividerItemDecoration = DividerItemDecoration(this@TestActivity, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        // Check the source of the activity
        val source = intent.getStringExtra("source")
        println("TEST ACTIVITY" +source)
        if (source == "UpdatePatientActivity" || source == null) {

            // Load all tests for a specific nurse
            loadTestsByNurseAndPatient(nurseId, patientId)
        } else {
           // Load tests for a specific patient and nurse
            loadTestsByNurse(nurseId)

        }

        // Set up FAB click listener
        binding.buttonAddTest.setOnClickListener {
            // Create a new activity to collect information about the new patient
            val intent = Intent(this, AddTestActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loadTestsByNurse(nurseId: Int)
    {
        // Get the patient DAO from the database
        val testDao = HospitalDatabase.getDatabase(this).testDao()

        // Retrieve the list of patients for the specified nurse from the database
        lifecycleScope.launch {
            val tests = testDao.getTestByNurseId(nurseId)
            println(" loadTestsByNurse")
            // Update the adapter with the new list of patients
            testAdapter.setData(tests as List<TestEntitiy>)
        }

    }



    private fun loadTestsByNurseAndPatient(nurseId: Int, patientId:Int) {
        // Get the patient DAO from the database
        val testDao = HospitalDatabase.getDatabase(this).testDao()

        // Retrieve the list of patients for the specified nurse from the database
        lifecycleScope.launch {
            val tests = testDao.getTestsByNurseandPatient(nurseId,patientId)
            println(" loadTestsByNurseAndPatient")
            // Update the adapter with the new list of patients
            testAdapter.setData(tests as List<TestEntitiy>)
        }
    }


    override fun onBackPressed() {
        // Navigate to TestActivity screen
        val source = intent.getStringExtra("source")
        val intent = if (source == "UpdatePatientActivity" || source==null) {
            Intent(this, PatientActivity::class.java)
        }
        else
        {
            Intent(this, WelcomeActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}