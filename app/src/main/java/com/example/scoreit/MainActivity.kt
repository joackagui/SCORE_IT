package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Partido
import com.example.scoreit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonPasar.setOnClickListener {
            val intentMain = Intent(this, ActivityMenuPrincipal::class.java)
            startActivity(intentMain)
        }

    }

    /*fun emparejamientoPartidos(campeonato: Campeonato){
        val listaDeEquipos = campeonato.listaDeEquipos
        val listaDePartidos = mutableListOf<Partido>()
        val numeroDeEquipos = listaDeEquipos.size
        val matrizDePartidos = Array(numeroDeEquipos){Array<Partido?>(numeroDeEquipos){null} }

        for (i in 0..<numeroDeEquipos) {
            matrizDePartidos[i][i] = null
            for (j in 0..<numeroDeEquipos) {
                val nuevoPartido = Partido(listaDeEquipos[i], listaDeEquipos[j], 0, 0, 0, 0, null, null, null)
                matrizDePartidos[i][j] = nuevoPartido
                if(campeonato.dosPartidos) {
                    val nuevoPartido2 = Partido(listaDeEquipos[j], listaDeEquipos[i], 0, 0,0,0, null, null, null)
                    matrizDePartidos[j][i] = nuevoPartido2
                }
            }
        }*/

}