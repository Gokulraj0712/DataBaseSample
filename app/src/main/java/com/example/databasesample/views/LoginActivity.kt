package com.example.databasesample.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.database.NurseEntity
import com.example.databasesample.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var hospitalDatabase: HospitalDatabase
    private val PREF_NURSE_ID = "pref_nurse_id"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hospitalDatabase = HospitalDatabase.getDatabase(this)

        sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            loginMethod()
        }

        binding.registerText.setOnClickListener {
            registerNurse()
        }

    }

    private fun loginMethod() {



            GlobalScope.launch {
                 var nurse:NurseEntity?=null
                val nurseid = binding.etNurseId.text.toString()
                val password = binding.etPassword.text.toString()

                withContext(Dispatchers.IO) {
                    nurse = hospitalDatabase.nurseDao().login(nurseid.toInt(), password)
                }
                if(nurse?.nurseId !=null)
                {
                Log.d("Nurse Data",nurse.toString())

                val nurseId = nurseid.toInt()
                val editor = sharedPreferences.edit()
                editor.putInt(PREF_NURSE_ID, nurseId)
                editor.apply()

                // Navigate to WelcomeActivity screen
                val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            }
                else{
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Invalid nurse ID or password", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    private fun registerNurse()
    {
        // Navigate to RegsiterNurseActivity screen
        val intent = Intent(this, RegsiterNurseActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}



