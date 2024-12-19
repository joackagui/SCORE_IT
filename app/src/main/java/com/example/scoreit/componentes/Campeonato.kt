package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Campeonato(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var logo: String? = null,
    var seleccionado: Boolean = false,
    var nombreCampeonato: String,
    var fechaDeInicio: String,
    var seJuegaPorPuntosMaximos: Boolean,
    var puntosParaGanar: Int?,
    var seJuegaPorTiempoMaximo: Boolean,
    var tiempoDeJuego: Int?,
    var modoDeJuego: String,
    var permisoDeDescanso: Boolean,
    var tiempoDeDescanso: Int?,
    var cantidadDeDescansos: Int?,
    var permisoDeRonda: Boolean,
    var cantidadDeRondas: Int?,
    var idaYVuelta: Boolean,
    var siempreUnGanador: Boolean,
    var diferenciaDosPuntos: Boolean
)
