package com.example.databasesample.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.NurseEntity
import com.example.databasesample.databinding.ActivityLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var hospitalDatabase: HospitalDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hospitalDatabase = HospitalDatabase.getDatabase(this)

        binding.btnLogin.setOnClickListener {
            loginMethod()
        }

        binding.registerText.setOnClickListener {
            registerNurse()
        }

    }

    private fun loginMethod() {
        val nurseid = binding.etNurseId.text.toString()
        val password = binding.etPassword.text.toString()

        if(nurseid.isNotEmpty())
        {
            lateinit var nurse:NurseEntity

            GlobalScope.launch {
                nurse=hospitalDatabase.nurseDao().login(nurseid.toInt(),password)
                Log.d("Nurse Data",nurse.toString())

                // Navigate to WelcomeActivity screen
                val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        else{
            Toast.makeText(this@LoginActivity,"Please enter the data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerNurse()
    {
        // Navigate to RegsiterNurseActivity screen
        val intent = Intent(this@LoginActivity, RegsiterNurseActivity::class.java)
        startActivity(intent)
        finish()
    }

}



