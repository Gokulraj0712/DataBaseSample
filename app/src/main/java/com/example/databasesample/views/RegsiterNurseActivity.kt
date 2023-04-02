package com.example.databasesample.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.NurseEntity
import com.example.databasesample.databinding.ActivityRegsiternurseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegsiterNurseActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegsiternurseBinding
    private lateinit var hospitalDatabase: HospitalDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegsiternurseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hospitalDatabase = HospitalDatabase.getDatabase(this)

        binding.btnRegister.setOnClickListener{
            insertNurse()
            // Navigate to LoginActivity screen
            val intent = Intent(this@RegsiterNurseActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun insertNurse() {
        val firstname = binding.etFirstName.text.toString()
        val lastname=binding.etLastName.text.toString()
        val department=binding.etDepartment.text.toString()
        val password=binding.etPassword.text.toString()

        if(firstname.isNotEmpty() && lastname.isNotEmpty() && department.isNotEmpty() && password.isNotEmpty())
        {
            val nurse= NurseEntity(
                null,firstname,lastname,department,password
            )
            GlobalScope.launch(Dispatchers.IO) {

                hospitalDatabase.nurseDao().insert(nurse)
            }
            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etDepartment.text.clear()
            binding.etPassword.text.clear()

            Toast.makeText(this,"Successfully written", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"PLease Enter Data",Toast.LENGTH_SHORT).show()
        }


    }

}