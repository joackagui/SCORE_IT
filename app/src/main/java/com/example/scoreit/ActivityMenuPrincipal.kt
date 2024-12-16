package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityCrearNuevoCampeonato.Companion.ID_USER
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDBAccess

class ActivityMenuPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityMenuPrincipalBinding

    private val recyclerCampeonatosCreados: RecyclerCampeonatosCreados by lazy {RecyclerCampeonatosCreados()}

    private val dbAccess = applicationContext as AppDBAccess

    companion object{
        val ID_EMAIL = "EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpRecyclerView()

        nuevoCampeonato()

        recyclerCampeonatosCreados.CampeonatoViewHolder().binding.botonEntrarAlCampeonato
    }

    private fun nuevoCampeonato() {
        binding.botonCrearNuevoCampeonato.setOnClickListener {
            val activityCrearNuevoCampeonato = Intent(this, ActivityCrearNuevoCampeonato::class.java)
            val correoUsuario = intent.getStringExtra(ID_EMAIL).toString()
            val user: Usuario = dbAccess.room.usuarioDao().obternerPorEmail(correoUsuario)
            activityCrearNuevoCampeonato.putExtra(ID_USER, user.id)

            startActivity(activityCrearNuevoCampeonato)
        }
    }

    fun setUpRecyclerView() {
        val correoUsuario = intent.getStringExtra(ID_EMAIL).toString()
        val user: Usuario = dbAccess.room.usuarioDao().obternerPorEmail(correoUsuario)
        val listaDeCampeonatosCreados = dbAccess.room.campeonatoDao().obtenerCampeonatosPorIdUsuario(user.id.toString())
        recyclerCampeonatosCreados.addDataToList(listaDeCampeonatosCreados)

        binding.recyclerCampeonatosCreados.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerCampeonatosCreados
        }

        val bienvenida: TextView = binding.campeonatosCreados
        bienvenida.text = "@string/campeonatos_creados_por_text"

    }
}