package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityCrearNuevoCampeonato.Companion.ID_USER
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import kotlinx.coroutines.launch

class ActivityMenuPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityMenuPrincipalBinding

    //private val recyclerCampeonatosCreados: RecyclerCampeonatosCreados by lazy {RecyclerCampeonatosCreados()}

    private lateinit var dbAccess: AppDataBase

    companion object{
        val USER_EMAIL: String = "EMAIL"
        val USER_NAME: String = "USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = getDatabase(this)
        lifecycleScope.launch {
            setUpRecyclerView()
            nuevoCampeonato()
        }
        desplegarTexto()
    }

    private fun nuevoCampeonato() {
        lifecycleScope.launch {
            val correoUsuario = intent.getStringExtra(USER_EMAIL).toString()
            val user = dbAccess.usuarioDao().obternerPorEmail(correoUsuario)
            cambioACrearNuevoCampeonato(user)
        }
    }

    private fun cambioACrearNuevoCampeonato(user: Usuario? = null) {
        binding.botonCrearNuevoCampeonato.setOnClickListener {
            val activityCrearNuevoCampeonato = Intent(this, ActivityCrearNuevoCampeonato::class.java)
            if (user != null) {
                activityCrearNuevoCampeonato.putExtra(ID_USER, user.id)
                activityCrearNuevoCampeonato.putExtra(USER_EMAIL, user.email)
            }
            startActivity(activityCrearNuevoCampeonato)
        }
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch {
            val correoUsuario = intent.getStringExtra(USER_EMAIL).toString()
            val user = dbAccess.usuarioDao().obternerPorEmail(correoUsuario)

            val listaDeCampeonatosCreados =
                dbAccess.campeonatoDao().obtenerCampeonatosPorIdUsuario(user?.id.toString())
            val adapter = RecyclerCampeonatosCreados()
            adapter.addDataToList(listaDeCampeonatosCreados)

            binding.recyclerCampeonatosCreados.apply {
                layoutManager = LinearLayoutManager(this@ActivityMenuPrincipal)
                this.adapter = adapter
            }

        }
    }

    fun desplegarTexto(){
        val bienvenida: TextView = binding.campeonatosCreados
        bienvenida.text = "Campeonatos creados por ${intent.getStringExtra(USER_NAME)}:"
    }
}