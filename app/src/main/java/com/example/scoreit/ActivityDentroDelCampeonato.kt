package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityDefinirEquipos.Companion.USER_EMAIL_DE
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
import com.example.scoreit.adapters.RecyclerPartidosCreados.RecyclerPartidosCreados
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivityDentroDelCampeonatoBinding
import kotlinx.coroutines.launch

class ActivityDentroDelCampeonato : AppCompatActivity() {

    private val recyclerPartidosCreados: RecyclerPartidosCreados by lazy { RecyclerPartidosCreados() }
    private lateinit var binding: ActivityDentroDelCampeonatoBinding
    private lateinit var dbAccess: AppDataBase

    companion object{
        const val ID_CAMPEONATO_DC = "CAMPEONATO"
        const val USER_EMAIL_DC = "USER_EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDentroDelCampeonatoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = getDatabase(this)

        configurarEncabezado()
        setUpNombreDelCampeonato()
        setUpRecyclerViewPartidos()
        volverMenuPrincipal()

    }

    private fun AppCompatActivity.configurarEncabezado() {
        val botonUsuario = findViewById<Button>(R.id.boton_de_usuario)
        val copaScoreIt = findViewById<ImageView>(R.id.copa_score_it)

        botonUsuario.setOnClickListener {
            val intent = Intent(this, ActivityLogIn::class.java)
            startActivity(intent)
        }

        copaScoreIt.setOnClickListener {
            val activityVolverMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
            activityVolverMenuPrincipal.putExtra(USER_EMAIL, intent.getStringExtra(USER_EMAIL_DC))
            startActivity(activityVolverMenuPrincipal)
        }
    }

    private fun volverMenuPrincipal(){
        binding.botonAtras.setOnClickListener{
            val activityVolverMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
            activityVolverMenuPrincipal.putExtra(USER_EMAIL, intent.getStringExtra(USER_EMAIL_DC))
            startActivity(activityVolverMenuPrincipal)
        }
    }

    private fun setUpNombreDelCampeonato() {
        lifecycleScope.launch {
            val campeonatoId = intent.getStringExtra(ID_CAMPEONATO_DC)
            if(campeonatoId != null){
                val nombreDelCampeonato = dbAccess.campeonatoDao()
                    .obtenerPorId(campeonatoId).nombreCampeonato
                binding.nombreDentroDelCampeonato.text = nombreDelCampeonato
            }
        }
    }

    private fun setUpRecyclerViewTabla(){

    }

    private fun setUpRecyclerViewPartidos() {
        lifecycleScope.launch {
            val campeonatoId = intent.getStringExtra(ID_CAMPEONATO_DC).toString()
            val listaDePartidosCreados = dbAccess.partidoDao().obtenerPartidosPorId(campeonatoId)
            recyclerPartidosCreados.addDataToList(listaDePartidosCreados)

            binding.recyclerPartidosCreados.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerPartidosCreados
            }
        }
    }
}