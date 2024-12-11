package com.example.scoreit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scoreit.databinding.ActivityPanelArbitrajeBinding


class ActivityPanelArbitraje : AppCompatActivity() {
    private lateinit var binding: ActivityPanelArbitrajeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPanelArbitrajeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.botonPrimerEquipo.setOnClickListener {
            binding.puntajePrimerEquipo.text = (binding.puntajePrimerEquipo.text.toString().toInt() + 1).toString()
        }
        binding.botonSegundoEquipo.setOnClickListener {
            binding.puntajeSegundoEquipo.text = (binding.puntajeSegundoEquipo.text.toString().toInt() + 1).toString()
        }

    }
}