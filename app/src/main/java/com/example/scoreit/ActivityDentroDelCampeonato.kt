package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
import com.example.scoreit.adapters.RecyclerPartidosCreados.RecyclerPartidosCreados
import com.example.scoreit.adapters.RecyclerTabla.RecyclerTabla
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivityDentroDelCampeonatoBinding
import kotlinx.coroutines.launch

class ActivityDentroDelCampeonato : AppCompatActivity() {

    private val recyclerPartidosCreados: RecyclerPartidosCreados by lazy { RecyclerPartidosCreados() }
    private val recyclerTabla: RecyclerTabla by lazy { RecyclerTabla() }
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
        setUpRecyclerTabla()
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
                binding.partidosText.text = "${binding.partidosText.text} ${dbAccess.partidoDao().obtenerPartidosPorId(campeonatoId).size}"
                binding.fechaText.text = "${binding.fechaText.text} ${dbAccess.campeonatoDao().obtenerPorId(campeonatoId).fechaDeInicio}"
                binding.modoDeJuegoText.text = "${binding.modoDeJuegoText.text}${dbAccess.campeonatoDao().obtenerPorId(campeonatoId).modoDeJuego}"
                binding.doblePartidoText.text = "${binding.doblePartidoText.text} ${dbAccess.campeonatoDao().obtenerPorId(campeonatoId).idaYVuelta}"
                binding.porRondasText.text = "${binding.porRondasText.text} ${dbAccess.campeonatoDao().obtenerPorId(campeonatoId).permisoDeRonda}"
                binding.siempreUnGanadorText.text = "${binding.siempreUnGanadorText.text} ${dbAccess.campeonatoDao().obtenerPorId(campeonatoId).siempreUnGanador}"
                binding.tiempoText.text = "${binding.tiempoText.text} ${dbAccess.campeonatoDao().obtenerPorId(campeonatoId).seJuegaPorTiempoMaximo}"
                binding.puntosText.text = "${binding.puntosText.text} ${dbAccess.campeonatoDao().obtenerPorId(campeonatoId).seJuegaPorPuntosMaximos}"
            }
        }
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
    private fun setUpRecyclerTabla(){
        lifecycleScope.launch {
            val campeonatoId = intent.getStringExtra(ID_CAMPEONATO_DC).toString()
            val listaDeEquipos = dbAccess.equipoDao().obtenerEquiposPorIdCampeonato(campeonatoId)

            val listaOrdenada = ordenarEquipos(listaDeEquipos)
            recyclerTabla.addDataToList(listaOrdenada.toMutableList())

            binding.recyclerTablaDePosiciones.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerTabla
            }
        }
    }

    private fun ordenarEquipos(equipos: List<Equipo>): List<Equipo> {
        return equipos.sortedWith(
            compareByDescending<Equipo> { it.puntosFinales }
                .thenByDescending { it.puntosInGame }
                .thenByDescending { it.partidosGanados }
        )
    }

}