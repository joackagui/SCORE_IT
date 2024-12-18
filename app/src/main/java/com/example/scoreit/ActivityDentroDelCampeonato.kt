package com.example.scoreit

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.adapters.RecyclerPartidosCreados.RecyclerPartidosCreados
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivityDentroDelCampeonatoBinding
import com.example.scoreit.databinding.ActivityLogInBinding
import kotlinx.coroutines.launch

//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase

class ActivityDentroDelCampeonato : AppCompatActivity() {
    private lateinit var binding: ActivityDentroDelCampeonatoBinding

    private val recyclerPartidosCreados: RecyclerPartidosCreados by lazy { RecyclerPartidosCreados() }

    private lateinit var dbAccess: AppDataBase

    companion object{
        val ID_CAMPEONATO = "CAMPEONATO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDentroDelCampeonatoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = getDatabase(this)
        lifecycleScope.launch {
            setUpNombreDelCampeonato()
            setUpRecyclerView()
        }




    }

    private fun setUpNombreDelCampeonato() {
        lifecycleScope.launch {
            val idDelCampeonato = intent.getStringExtra(ID_CAMPEONATO)
            val nombreDelCampeonato = dbAccess.campeonatoDao()
                .obtenerPorId(idDelCampeonato.toString()).nombreCampeonato
            binding.nombreDentroDelCampeonato.text = nombreDelCampeonato
        }
    }

    fun setUpRecyclerView() {
        lifecycleScope.launch {
            val campeonatoId = intent.getStringExtra(ID_CAMPEONATO).toString()
            val listaDePartidosCreados = dbAccess.partidoDao().obtenerPartidosPorId(campeonatoId)
            recyclerPartidosCreados.addDataToList(listaDePartidosCreados)

            binding.recyclerPartidosCreados.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerPartidosCreados
            }
        }



    }

}