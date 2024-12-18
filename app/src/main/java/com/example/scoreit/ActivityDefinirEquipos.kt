package com.example.scoreit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.componentes.Partido
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.database.Converters
import com.example.scoreit.databinding.ActivityDefinirEquiposBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch


class ActivityDefinirEquipos : AppCompatActivity() {
    private lateinit var binding: ActivityDefinirEquiposBinding

    private lateinit var dbAccess: AppDataBase

    companion object{
        val CAMPEONATO: String = "CAMPEONATO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefinirEquiposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var num = 0

        dbAccess = getDatabase(this)
        lifecycleScope.launch {
            num = insertarEquipo(true, num)

            botonPresionado(num)
        }

//        binding.botonCrearCampeonato.setOnClickListener{
//            crearFixture(intent.getStringExtra(ID_CAMPEONATO).toString())
//        }
    }

    private fun insertarEquipo(primeraVez: Boolean, num: Int): Int{
        val campeonato = Converters().toCampeonato(intent.getStringExtra(CAMPEONATO))
        var actualizador = num
        lifecycleScope.launch {
            if(primeraVez){
                if (campeonato != null) {
                    dbAccess.equipoDao().insert(
                        Equipo(nombre = "Equipo $actualizador", idCampeonato = campeonato.id))
                    actualizador += 1
                    dbAccess.equipoDao().insert(
                        Equipo(nombre = "Equipo $actualizador", idCampeonato = campeonato.id))
                    actualizador += 1
                }

            } else {
                if (campeonato != null) {
                    dbAccess.equipoDao().insert(
                        Equipo(nombre = "Equipo $actualizador", idCampeonato = campeonato.id))
                    actualizador += 1
                }
            }
        }
        return actualizador
    }

    private fun botonPresionado(num: Int){
        binding.botonAnadirEquipo.setOnClickListener{
            insertarEquipo(false, num)
        }
    }

    /*private fun crearFixture(idCampeonato: String) {
        val listaDeEquipos = dbAccess.room.equipoDao().obtenerEquiposPorId(idCampeonato)
        val campeonato = dbAccess.room.campeonatoDao().obtenerPorId(idCampeonato)
        val listaDeEquiposId = mutableListOf<String>()
        val partidos = mutableListOf<Partido>()

        val tiempoDeJuego = if (campeonato.seJuegaPorTiempoMaximo) campeonato.tiempoDeJuego else null
        val cantidadDeDescansos = if (campeonato.permisoDeDescanso) campeonato.cantidadDeDescansos else 0

        for (i in 0..listaDeEquipos.size) {
            listaDeEquiposId.add(listaDeEquipos[i].id.toString())
        }

        if (listaDeEquipos.size < 2) throw IllegalArgumentException("Se necesitan al menos 2 equipos")

        val esImpar = listaDeEquipos.size % 2 != 0
        if (esImpar) {
            listaDeEquiposId.add("Descanso")
        }

        val totalFechas = listaDeEquiposId.size - 1

        for (jornada in 1..totalFechas) {
            for (i in 0 until listaDeEquiposId.size / 2) {
                val local = listaDeEquiposId[i]
                val visitante = listaDeEquiposId[listaDeEquiposId.size - 1 - i]

                if (local != "Descanso" && visitante != "Descanso") {
                    partidos.add(
                        Partido(
                        jornada = jornada,
                        primerEquipoId = dbAccess.room.equipoDao().obtenerPorId(local).id,
                        segundoEquipoId = dbAccess.room.equipoDao().obtenerPorId(visitante).id,
                        puntosPrimerEquipo = 0,
                        puntosSegundoEquipo = 0,
                        rondasPrimerEquipo = 0,
                        rondasSegundoEquipo = 0,
                        idCampeonato = idCampeonato.toInt()
                        )
                    )
                }
            }

            val ultimo = listaDeEquipos.removeAt(listaDeEquipos.size - 1)
            listaDeEquipos.add(1, ultimo)
        }
        dbAccess.room.partidoDao().insertarPartidos(partidos)
    }*/
}