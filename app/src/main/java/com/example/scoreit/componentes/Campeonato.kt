package com.example.scoreit.componentes

data class Campeonato(
    var nombre: String,
    val fecha: String,
    val listaDeEquipos: MutableList<Equipo> = mutableListOf(),
    val dosPartidos: Boolean,
    val soloUnGanador: Boolean,
    val listaDePartidos: MutableList<Partido> = mutableListOf(),
    val diferenciaDosPuntos: Boolean
)
