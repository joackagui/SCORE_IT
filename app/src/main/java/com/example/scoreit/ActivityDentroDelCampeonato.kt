package com.example.scoreit

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityMenuPrincipal.Companion.ID_EMAIL
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.adapters.RecyclerPartidosCreados.RecyclerPartidosCreados
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDBAccess
import com.example.scoreit.databinding.ActivityDentroDelCampeonatoBinding
import com.example.scoreit.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActivityDentroDelCampeonato : AppCompatActivity() {
    private lateinit var binding: ActivityDentroDelCampeonatoBinding

    private val recyclerPartidosCreados: RecyclerPartidosCreados by lazy { RecyclerPartidosCreados() }

    private val dbAccess = applicationContext as AppDBAccess

    companion object{
        val ID_CAMPEONATO = "CAMPEONATO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDentroDelCampeonatoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpRecyclerView()
        setUpNombreDelCampeonato()


    }

    private fun setUpNombreDelCampeonato() {
        val idDelCampeonato = intent.getStringExtra(ID_CAMPEONATO)
        val nombreDelCampeonato = dbAccess.room.campeonatoDao().obtenerPorId(idDelCampeonato.toString()).nombreCampeonato
        binding.nombreDentroDelCampeonato.text = nombreDelCampeonato
    }

    fun setUpRecyclerView() {
        val campeonatoId = intent.getStringExtra(ID_CAMPEONATO).toString()

        val listaDePartidosCreados = dbAccess.room.partidoDao().obtenerPartidosPorId(campeonatoId)
        recyclerPartidosCreados.addDataToList(listaDePartidosCreados)

        binding.recyclerPartidosCreados.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerPartidosCreados
        }


    }

}