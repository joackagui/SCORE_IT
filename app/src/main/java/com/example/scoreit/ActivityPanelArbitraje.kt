package com.example.scoreit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.scoreit.componentes.Partido
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivityPanelArbitrajeBinding
import kotlinx.coroutines.launch


class ActivityPanelArbitraje : AppCompatActivity() {

    private lateinit var binding: ActivityPanelArbitrajeBinding
    private lateinit var dbAccess: AppDataBase
    companion object {
        const val ID_PARTIDO_PA: String = "PARTIDO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanelArbitrajeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)
        lifecycleScope.launch {
            evaluacionDelPartido()
            obtenerTiempoDeJuego()
            descanso()
        }


        aumentarPuntos()

        binding.botonDescansoSegundoEquipo.setOnClickListener {

        }

    }

    private fun descanso(){
        lifecycleScope.launch {
            val hayDescansos = dbAccess.partidoDao().obtenerSiPartidoPorPuntos("ID_PARTIDO")
            binding.botonDescansoPrimerEquipo.setOnClickListener {
                if (hayDescansos) {

                }
            }
        }
    }

    private fun obtenerTiempoDeJuego() {
        lifecycleScope.launch {
            dbAccess.partidoDao()
                .obtenerTiempoDelPartido(intent.getStringExtra(ID_PARTIDO_PA).toString())
        }
    }

    private fun evaluacionDelPartido() {
        lifecycleScope.launch {
            val idpartido: String = intent.getStringExtra(ID_PARTIDO_PA).toString()
            val partidoActual =
                dbAccess.partidoDao().obtenerPorId(idpartido)
            val tieneMaximoDePuntos = dbAccess.partidoDao().obtenerSiPartidoPorPuntos(idpartido)
            val maximoDePuntos = dbAccess.partidoDao()
                .obtenerPuntosParaGanar(idpartido)
            val tieneTiempoDeJuego = dbAccess.partidoDao()
                .obtenerSiPartidoPorTiempo(idpartido)
            val siempreGanador = dbAccess.partidoDao()
                .obtenerSiempreUnGanador(idpartido)
            var cantidadDescansosPrimerEquipo = dbAccess.partidoDao()
                .obtenerCantidadDescansos(idpartido)
            var cantidadDescansosSegundoEquipo = cantidadDescansosPrimerEquipo
            var diferenciaDeDosPuntos = dbAccess.partidoDao()
                .obtenerSiHayDiferenciaDeDosPuntos(idpartido)
            var tiempoDelPartido = dbAccess.partidoDao()
                .obtenerTiempoDelPartido(idpartido)

            arbitrar(
                partidoActual,
                tieneMaximoDePuntos,
                maximoDePuntos,
                tieneTiempoDeJuego,
                siempreGanador,
                diferenciaDeDosPuntos,
                cantidadDescansosPrimerEquipo,
                cantidadDescansosSegundoEquipo,
                tiempoDelPartido
            )
        }
    }

    private fun arbitrar(partidoActual: Partido,
        tieneMaximoDePuntos: Boolean,
        maximoDePuntos: Int,
        tieneTiempoDeJuego: Boolean,
        siempreGanador: Boolean,
        diferenciaDeDosPuntos: Boolean,
        cantidadDescansosPrimerEquipo: Int,
        cantidadDescansosSegundoEquipo: Int, tiempoDelPartido: Int) {
        if (partidoActual.puntosPrimerEquipo.toInt() >= maximoDePuntos || partidoActual.puntosSegundoEquipo.toInt() >= maximoDePuntos) {
            finalizar()
            //stopTimer()
        }
        if (tieneTiempoDeJuego && tieneMaximoDePuntos && siempreGanador && diferenciaDeDosPuntos) {

        } else if (tieneTiempoDeJuego && tieneMaximoDePuntos && siempreGanador && !diferenciaDeDosPuntos) {

        } else if (tieneTiempoDeJuego && !tieneMaximoDePuntos && siempreGanador) {

        } else if (tieneTiempoDeJuego && !tieneMaximoDePuntos && !siempreGanador) {

        } else if (!tieneTiempoDeJuego && tieneMaximoDePuntos && siempreGanador) {
            if(diferenciaDeDosPuntos){

            } else {

            }

        } else if (!tieneTiempoDeJuego && tieneMaximoDePuntos) {
            if(!diferenciaDeDosPuntos){

            } else {

            }

        } else if (!tieneTiempoDeJuego && !tieneMaximoDePuntos && siempreGanador && !diferenciaDeDosPuntos) {
            if(partidoActual.puntosPrimerEquipo != partidoActual.puntosSegundoEquipo){
                finalizar()
            }
        } else if (!tieneTiempoDeJuego && !tieneMaximoDePuntos && !siempreGanador && !diferenciaDeDosPuntos) {
            finalizar()
        }
    }

    fun finalizarManualmente(){
        lifecycleScope.launch {
            binding.botonFinalizarPartido.setOnClickListener{
                finalizar()
            }
        }
    }

    private fun finalizar() {

    }

    private fun aumentarPuntos() {
        binding.botonPrimerEquipo.setOnClickListener {
            binding.puntajePrimerEquipo.text =
                (binding.puntajePrimerEquipo.text.toString().toInt() + 1).toString()
        }
        binding.botonSegundoEquipo.setOnClickListener {
            binding.puntajeSegundoEquipo.text =
                (binding.puntajeSegundoEquipo.text.toString().toInt() + 1).toString()
        }
    }
}

