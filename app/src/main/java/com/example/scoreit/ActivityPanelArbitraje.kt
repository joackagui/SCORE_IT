package com.example.scoreit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.databinding.ActivityPanelArbitrajeBinding


class ActivityPanelArbitraje : AppCompatActivity() {
    private lateinit var binding: ActivityPanelArbitrajeBinding

    private lateinit var dbAccess: AppDataBase

    companion object{
        val ID_PARTIDO: String = "PARTIDO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPanelArbitrajeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        evaluacionDelPartido()
        aumentarPuntos()

        binding.botonDescansoSegundoEquipo.setOnClickListener {

        }

        binding.botonDescansoPrimerEquipo.setOnClickListener {
            if(dbAccess.partidoDao().obtenerSiPartidoPorPuntos("ID_PARTIDO")){

            }
        }

    }

    private fun evaluacionDelPartido() {
        val partidoActual = dbAccess.partidoDao().obtenerPorId(intent.getStringExtra(ID_PARTIDO).toString())
        val maximoDePuntos = dbAccess.partidoDao().obtenerPuntosParaGanar(intent.getStringExtra(ID_PARTIDO).toString())
        val tieneTiempoDeJuego = dbAccess.partidoDao().obtenerSiPartidoPorTiempo(intent.getStringExtra(ID_PARTIDO).toString())
        val siempreGanador = dbAccess.partidoDao().obtenerSiempreUnGanador(intent.getStringExtra(ID_PARTIDO).toString())
        var cantidadDescansosPrimerEquipo = dbAccess.partidoDao().obtenerCantidadDescansos(intent.getStringExtra(ID_PARTIDO).toString())
        var cantidadDescansosSegundoEquipo = cantidadDescansosPrimerEquipo
        var diferenciaDeDosPuntos = dbAccess.partidoDao().obtenerSiHayDiferenciaDeDosPuntos(intent.getStringExtra(ID_PARTIDO).toString())

        /*if(partidoActual.puntosPrimerEquipo >= maximoDePuntos || partidoActual.puntosSegundoEquipo >= maximoDePuntos){
            finDelPartido()
            stopTimer()
        }
        if (tieneTiempoDeJuego){

        } else {
            if(siempreGanador){
                if(diferenciaDeDosPuntos){
                    finDelPartido()
                }
            } else {
                finDelPartido()
            }
        }

        if(partidoActual.tiempoDeJuegoRestante == 0){
            if(partidoActual.puntosPrimerEquipo != partidoActual.puntosSegundoEquipo){
                finDelPartido()
            } else if(!siempreGanador){
                finDelPartido()
            }
        }*/
    }

    private fun aumentarPuntos(){
        binding.botonPrimerEquipo.setOnClickListener {
            binding.puntajePrimerEquipo.text = (binding.puntajePrimerEquipo.text.toString().toInt() + 1).toString()
        }
        binding.botonSegundoEquipo.setOnClickListener {
            binding.puntajeSegundoEquipo.text = (binding.puntajeSegundoEquipo.text.toString().toInt() + 1).toString()
        }
    }

    /*private fun finDelPartido() {
        val nuevoPartido = Partido()
        dbAccess.room.partidoDao().update(nuevoPartido)
    }*/
}