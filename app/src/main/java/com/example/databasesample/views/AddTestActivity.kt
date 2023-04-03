package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
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

        val patientId = sharedPreferences.getInt(PREF_PATIENT_ID, -1)
        println("PAtientID: " +patientId)

        insertPatientName(this)

        binding.buttonSave.setOnClickListener {

            insertTest(binding,this)
        }
    }

    fun insertPatientName(context: Context) {

        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val patientId = sharedPreferences.getInt(PREF_PATIENT_ID, -1)

        if (patientId != -1) {
            GlobalScope.launch(Dispatchers.Main) {

                hospitalDatabase = HospitalDatabase.getDatabase(this@AddTestActivity)
                val patient = withContext(Dispatchers.IO) {
                    hospitalDatabase.patientDao().getPatientById(patientId)
                }
                binding.editTextName.setText(patient.firstname + " " + patient.lastname)
                insertPatientName(this@AddTestActivity)
            }
        }
        if(binding.editTextName.text.toString().isEmpty()) {
            binding.editTextId.visibility = View.VISIBLE
            binding.editTextName.visibility=View.GONE
        }
        else
        {
            binding.editTextId.visibility = View.GONE
            binding.editTextName.visibility=View.VISIBLE
            }

    }

     fun insertTest(binding: ActivityAddTestsBinding,context: Context)
    {
        val bpl = binding.editTextBpl.text.toString()
        val bph = binding.editTextBph.text.toString()
        val temp = binding.editTextTemp.text.toString()
        val nurse_id = sharedPreferences.getInt(PREF_NURSE_ID, -1)
        val patientid = sharedPreferences.getInt(PREF_PATIENT_ID, -1)
        val patient_ID = binding.editTextId.text.toString()
        val test: TestEntitiy?

        if(bpl.isNotEmpty() && bph.isNotEmpty() && temp.isNotEmpty())
        {
            if(binding.editTextId.visibility==View.GONE) {
                 test = TestEntitiy(
                    null,
                    bpl.toDouble(),
                    bph.toDouble(),
                    temp.toDouble(),
                    nurse_id,
                     patientid

                )
            }
            else
            {
                 test = TestEntitiy(
                    null,
                    bpl.toDouble(),
                    bph.toDouble(),
                    temp.toDouble(),
                    nurse_id,
                     patient_ID.toInt()
                )
            }
            GlobalScope.launch(Dispatchers.IO) {

                hospitalDatabase.testDao().upsert(test)
            }

            binding.editTextBpl.text.clear()
            binding.editTextBph.text.clear()
            binding.editTextTemp.text.clear()

            GlobalScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddTestActivity, "TEST SAVED SUCCESSFULLY ", Toast.LENGTH_SHORT).show()
                    // Navigate to TestActivity screen
                    val intent = Intent(this@AddTestActivity, TestActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
        else{
            Toast.makeText(this,"PLEASE ENTER ALL DATA", Toast.LENGTH_SHORT).show()
        }


    }

}