package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.databasesample.adapters.PatientAdapter
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.PatientEntitiy
import com.example.databasesample.databinding.ActivityAddPatientBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdatePatientActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPatientBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var hospitalDatabase: HospitalDatabase
    private val PREF_NURSE_ID = "pref_nurse_id"
    private val PREF_PATIENT_ID = "pref_patient_id"
    private lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hospitalDatabase = HospitalDatabase.getDatabase(this)
        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val nurse = sharedPreferences.getInt(PREF_NURSE_ID, -1)
        val patient_id = sharedPreferences.getInt(PREF_PATIENT_ID,-1)
        patientAdapter = PatientAdapter()

                binding.buttonAddTest.visibility =View.VISIBLE

        loadPatient(patient_id)
        binding.buttonAddTest.setOnClickListener{

            val intent = Intent(this@UpdatePatientActivity, TestActivity::class.java)
            intent.putExtra("source", "UpdatePatientActivity")
            startActivity(intent)
            finish()
        }

        binding.buttonGoBack.setOnClickListener {
            val intent = Intent(this@UpdatePatientActivity, PatientActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonSave.setOnClickListener {

            updatePatient()
        }


    }

    private fun loadPatient(patient_id: Int) {
        // Get the patient DAO from the database
        val patientDao = HospitalDatabase.getDatabase(this).patientDao()

        // Retrieve the list of patients for the specified nurse from the database
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                // Retrieve the patient from the database on a background thread
                val patient = patientDao.getPatientById(patient_id)

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    val firstname = binding.editTextFirstName
                    val lastname = binding.editTextLastName
                    val department = binding.editTextDepartment
                    val room = binding.editTextroom

                    firstname.setText(patient.firstname)
                    lastname.setText(patient.lastname)
                    department.setText(patient.department)
                    room.setText(patient.room)
                }


            }
        }
    }


    private fun updatePatient() {
        val firstname = binding.editTextFirstName.text.toString()
        val lastname = binding.editTextLastName.text.toString()
        val department = binding.editTextDepartment.text.toString()
        val room = binding.editTextroom.text.toString()
        val nurse_id = sharedPreferences.getInt(PREF_NURSE_ID, -1)
        val patient_id = sharedPreferences.getInt(PREF_PATIENT_ID,-1)


        if (firstname.isNotEmpty() && lastname.isNotEmpty() && department.isNotEmpty() && room.isNotEmpty()) {
            val patient = PatientEntitiy(
                patient_id, firstname, lastname, department, room, nurse_id
            )
            GlobalScope.launch(Dispatchers.IO) {

                hospitalDatabase.patientDao().updatePatientEntity(patient_id,firstname,lastname,room,department)
            }

            binding.editTextFirstName.isEnabled = false
            binding.editTextLastName.isEnabled=false
            binding.editTextDepartment.isEnabled=false
            binding.editTextroom.isEnabled=false

            Toast.makeText(this, "PATIENT DETAILS SAVED SUCCESSFULLY ", Toast.LENGTH_SHORT).show()
            // Navigate to LoginActivity screen

                    } else {
            Toast.makeText(this, "PLEASE ENTER ALL DATA", Toast.LENGTH_SHORT).show()
        }


    }

}