package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.NurseEntity
import com.example.databasesample.database.PatientEntitiy
import com.example.databasesample.databinding.ActivityAddPatientBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddPatientActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPatientBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var hospitalDatabase: HospitalDatabase
    private val PREF_NURSE_ID = "pref_nurse_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hospitalDatabase = HospitalDatabase.getDatabase(this)
        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val nurse = sharedPreferences.getInt(PREF_NURSE_ID, -1)

        binding.buttonSave.setOnClickListener {

            insertPatient()
        }

        binding.buttonGoBack.setOnClickListener {
            val intent = Intent(this@AddPatientActivity, PatientActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun insertPatient()
    {
        val firstname = binding.editTextFirstName.text.toString()
        val lastname = binding.editTextLastName.text.toString()
        val department = binding.editTextDepartment.text.toString()
        val room = binding.editTextroom.text.toString()
        val nurse_id = sharedPreferences.getInt(PREF_NURSE_ID, -1)


        if(firstname.isNotEmpty() && lastname.isNotEmpty() && department.isNotEmpty() && room.isNotEmpty())
        {
            val patient= PatientEntitiy(
                null, firstname,lastname,department,room,nurse_id
            )
            GlobalScope.launch(Dispatchers.IO) {

                hospitalDatabase.patientDao().insert(patient)
            }

            binding.editTextFirstName.text.clear()
            binding.editTextLastName.text.clear()
            binding.editTextDepartment.text.clear()
            binding.editTextroom.text.clear()

            Toast.makeText(this,"PATIENT DETAILS SAVED SUCCESSFULLY ", Toast.LENGTH_SHORT).show()
            // Navigate to LoginActivity screen
            val intent = Intent(this@AddPatientActivity, PatientActivity::class.java)
            startActivity(intent)
            finish()

        }
        else{
            Toast.makeText(this,"PLEASE ENTER ALL DATA", Toast.LENGTH_SHORT).show()
        }


    }

}