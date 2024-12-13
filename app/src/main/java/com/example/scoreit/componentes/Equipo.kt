package com.example.scoreit.componentes

data class Equipo(
    val name: String,
    val listaDeJugadores: MutableList<String> = mutableListOf(),
    var puntos: Int = 0,
    var canchas: Int = 0
    )
