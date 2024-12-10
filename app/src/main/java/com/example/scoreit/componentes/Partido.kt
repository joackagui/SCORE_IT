package com.example.scoreit.componentes

data class Partido(
    val equipoLocal: String,
    val equipoVisitante: String,
    var puntosLocal: Int,
    var puntosVisitante: Int)
