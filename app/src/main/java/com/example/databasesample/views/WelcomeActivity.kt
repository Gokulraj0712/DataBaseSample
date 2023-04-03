package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.databasesample.databinding.ActivityWelcomeBinding
import kotlin.concurrent.thread

class WelcomeActivity: AppCompatActivity() {
    private lateinit var binding:ActivityWelcomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPatients.setOnClickListener{
            // Navigate to PatientActivity screen
            val intent = Intent(this, PatientActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnTests.setOnClickListener{
            val PREF_PATIENT_ID = "pref_patient_id"
            lateinit var sharedPreferences: SharedPreferences
            sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(PREF_PATIENT_ID, -1)
            editor.apply()

            // Navigate to TestActivity screen

            val intent = Intent(this, TestActivity::class.java).apply {
                putExtra("source", "WelcomeActivity")
            }
            startActivity(intent)
            finish()
        }

        binding.btnLogout.setOnClickListener {
            // Navigate to LoginActivity screen
            Toast.makeText(this,"Log Out Successful", Toast.LENGTH_SHORT).show()
            Thread.sleep(3000) // waits for 3 seconds

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}