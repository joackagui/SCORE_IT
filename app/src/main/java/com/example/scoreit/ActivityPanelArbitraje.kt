package com.example.scoreit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scoreit.databinding.ActivityCrearNuevoCampeonatoBinding

class ActivityPanelArbitraje : AppCompatActivity() {
    private lateinit var binding: ActivityCrearNuevoCampeonatoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCrearNuevoCampeonatoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}