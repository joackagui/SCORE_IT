package com.example.scoreit

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.databinding.ActivityCrearNuevoCmapeonatoBinding

class ActivityCrearNuevoCmapeonato : AppCompatActivity() {
    private lateinit var binding: ActivityCrearNuevoCmapeonatoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearNuevoCmapeonatoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val modoDeJuego = resources.getStringArray(R.array.opciones_para_modo_de_juego)

        val adapter = ArrayAdapter(this, R.layout.spinner_item_style, modoDeJuego)

// Configurar el Spinner con el Adapter
        binding.spinnerModoJuego.adapter = adapter


    }
}