package com.example.scoreit.componentes

data class Partido(
    val equipoLocal: Equipo,
    val equipoVisitante: Equipo,
    var puntosLocal: Int,
    var puntosVisitante: Int,
    var canchasLocal: Int,
    var canchasVisitante: Int,
    var tiempoRestante: Int?,
    var tiempoLocal: Int?,
    var tiempoVisitante: Int?
)
