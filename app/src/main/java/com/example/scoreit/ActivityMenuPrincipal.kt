package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding

class ActivityMenuPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityMenuPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.botonCrearNuevoCampeonato.setOnClickListener {
            val activityCrearNuevoCampeonato = Intent(this, ActivityCrearNuevoCampeonato::class.java)
            startActivity(activityCrearNuevoCampeonato)
        }
    }
}