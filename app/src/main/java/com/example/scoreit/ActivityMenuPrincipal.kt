package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityCrearNuevoCampeonato.Companion.ID_USER_NC
import com.example.scoreit.ActivityCrearNuevoCampeonato.Companion.USER_EMAIL_NC
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import kotlinx.coroutines.launch

class ActivityMenuPrincipal : AppCompatActivity() {

    private val recyclerCampeonatosCreados: RecyclerCampeonatosCreados by lazy {RecyclerCampeonatosCreados()}
    private lateinit var binding: ActivityMenuPrincipalBinding
    private lateinit var dbAccess: AppDataBase

    companion object{
        const val USER_EMAIL: String = "EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = getDatabase(this)

        configurarEncabezado()
        desplegarTexto()
        setUpRecyclerView()
        botonCampeonato()
    }

    private fun AppCompatActivity.configurarEncabezado() {
        val botonUsuario = findViewById<Button>(R.id.boton_de_usuario)

        botonUsuario.setOnClickListener {
            val intent = Intent(this, ActivityLogIn::class.java)
            startActivity(intent)
        }

    }

    private fun botonCampeonato(){
        lifecycleScope.launch {
            val correoUsuario = intent.getStringExtra(USER_EMAIL)
            if(correoUsuario != null){
                val user = dbAccess.usuarioDao().obternerPorEmail(correoUsuario)
                if (user != null) {
                    cambioACrearNuevoCampeonato(user)
                }
            }
        }
    }

    private fun cambioACrearNuevoCampeonato(user: Usuario) {
        binding.botonCrearNuevoCampeonato.setOnClickListener {
            val activityCrearNuevoCampeonato = Intent(this, ActivityCrearNuevoCampeonato::class.java)
            activityCrearNuevoCampeonato.putExtra(ID_USER_NC, user.id)
            activityCrearNuevoCampeonato.putExtra(USER_EMAIL_NC, user.email)
            startActivity(activityCrearNuevoCampeonato)
        }
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch {
            val listaDeCampeonatosCreados = dbAccess.campeonatoDao().obtenerTodosLosCampeonatos()
            recyclerCampeonatosCreados.addDataToList(listaDeCampeonatosCreados)

            binding.recyclerCampeonatosCreados.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerCampeonatosCreados

            }
        }
    }

    private fun desplegarTexto(){
        lifecycleScope.launch {
            val bienvenida: TextView = binding.campeonatosCreados
            bienvenida.text = "Campeonatos creados por ${dbAccess.usuarioDao().obternerPorEmail(intent.getStringExtra(USER_EMAIL).toString())?.nombreUsuario}:"
        }
    }
}