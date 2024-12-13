package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding
import com.example.scoreit.componentes.Campeonato

class ActivityMenuPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityMenuPrincipalBinding
    private val recyclerCampeonatosCreados: RecyclerCampeonatosCreados by lazy {RecyclerCampeonatosCreados()}

    companion object{
        val ID_USUARIO = "NOMBRE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpRecyclerView()

        binding.botonCrearNuevoCampeonato.setOnClickListener {
            val activityCrearNuevoCampeonato = Intent(this, ActivityCrearNuevoCampeonato::class.java)
            startActivity(activityCrearNuevoCampeonato)
        }
    }

    fun setUpRecyclerView() {
        val listaDeCampeonatosCreados = mutableListOf(Campeonato("Camp1", "12-12-12", mutableListOf(), true, true, mutableListOf(), true))
        recyclerCampeonatosCreados.addDataToList(listaDeCampeonatosCreados)

        binding.recyclerCampeonatosCreados.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerCampeonatosCreados
        }
        val bienvenida: TextView = findViewById(R.id.campeonatos_creados)
        bienvenida.text = "Campeonatos creados por ${intent.getStringExtra(ID_USUARIO)}:"

    }
}