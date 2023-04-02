package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.databasesample.R
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.NurseEntity
import com.example.databasesample.database.PatientEntitiy
import com.example.databasesample.database.TestEntitiy

import com.example.databasesample.databinding.ActivityAddTestsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTestActivity:AppCompatActivity() {


    private lateinit var binding: ActivityAddTestsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var hospitalDatabase: HospitalDatabase
    private val PREF_NURSE_ID = "pref_nurse_id"
    private val PREF_PATIENT_ID = "pref_patient_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hospitalDatabase = HospitalDatabase.getDatabase(this)
        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val nurse = sharedPreferences.getInt(PREF_NURSE_ID, -1)


        insertPatientName(this)

        binding.buttonSave.setOnClickListener {

            insertTest(binding,this)
        }
    }

    fun insertPatientName(context: Context) {

        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val patientId = sharedPreferences.getInt(PREF_PATIENT_ID, -1)
        GlobalScope.launch(Dispatchers.Main) {
            hospitalDatabase = HospitalDatabase.getDatabase(this@AddTestActivity)
            val patient = withContext(Dispatchers.IO) {
                hospitalDatabase.patientDao().getPatientById(patientId)
            }
            binding.editTextName.setText(patient.firstname + " " + patient.lastname)
            insertPatientName(this@AddTestActivity)
        }
    }

     fun insertTest(binding: ActivityAddTestsBinding,context: Context)
    {
        val bpl = binding.editTextBpl.text.toString()
        val bph = binding.editTextBph.text.toString()
        val temp = binding.editTextTemp.text.toString()
        val nurse_id = sharedPreferences.getInt(PREF_NURSE_ID, -1)
        val patientId = sharedPreferences.getInt(PREF_PATIENT_ID, -1)


        if(bpl.isNotEmpty() && bph.isNotEmpty() && temp.isNotEmpty())
        {
            val test= TestEntitiy(
                null, bpl.toDouble(),bph.toDouble(),temp.toDouble(),nurse_id,patientId
            )
            GlobalScope.launch(Dispatchers.IO) {

                hospitalDatabase.testDao().upsert(test)
            }

            binding.editTextBpl.text.clear()
            binding.editTextBph.text.clear()
            binding.editTextTemp.text.clear()

            Toast.makeText(this,"Successfully written", Toast.LENGTH_SHORT).show()
            // Navigate to TestActivity screen
            val intent = Intent(this@AddTestActivity, TestActivity::class.java)
            startActivity(intent)
            finish()

        }
        else{
            Toast.makeText(this,"PLease Enter Data", Toast.LENGTH_SHORT).show()
        }


    }

}