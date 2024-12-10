package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding
import com.example.scoreit.componentes.Campeonato

class ActivityMenuPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityMenuPrincipalBinding
    private val recycler by lazy {RecyclerCampeonatosCreados()}

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
        val listaDeCampeonatosCreados = mutableListOf(Campeonato("Camp1"))
        recycler.addDataToList(listaDeCampeonatosCreados)

        binding.recyclerCampeonatosCreados.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recycler
        }

    }
}