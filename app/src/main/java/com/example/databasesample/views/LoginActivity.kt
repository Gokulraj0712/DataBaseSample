package com.example.databasesample.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.databasesample.database.HospitalDatabase
import com.example.databasesample.databinding.ActivityLoginBinding
import com.example.databasesample.repo.NurseRepository
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var nurseRepository: NurseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = HospitalDatabase.getDatabase(applicationContext)
        nurseRepository = NurseRepository(database.nurseDao())

        binding.btnLogin.setOnClickListener {
            val nurseId = binding.etNurseId.text.toString()
            val password = binding.etPassword.text.toString()

            // Call login method of the repository to check if the nurseId and password match
            lifecycleScope.launch {
                val nurse = nurseRepository.login(nurseId, password)
                if (nurse != null) {
                    // If login is successful, start MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If login fails, show an error message
                    binding.etPassword.error = "Invalid nurseId or password"
                }
            }
        }
    }
}




