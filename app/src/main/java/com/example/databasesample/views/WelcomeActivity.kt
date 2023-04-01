package com.example.databasesample.views

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.databasesample.databinding.ActivityWelcomeBinding

class WelcomeActivity: AppCompatActivity() {
    private lateinit var binding:ActivityWelcomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}