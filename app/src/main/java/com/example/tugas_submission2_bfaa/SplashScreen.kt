package com.example.tugas_submission2_bfaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugas_submission2_bfaa.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}