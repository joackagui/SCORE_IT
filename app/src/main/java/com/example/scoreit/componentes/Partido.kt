package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(entity = Campeonato::class,
        parentColumns = ["id"],
        childColumns = ["idCampeonato"],
        onDelete = ForeignKey.CASCADE)
    ]
)

data class Partido(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val equipoLocal: Equipo,
    val equipoVisitante: Equipo,
    var puntosLocal: Int,
    var puntosVisitante: Int,
    var tiempoRestante: Int?,
    var tiempoLocal: Int?,
    var tiempoVisitante: Int?,
    var canchasLocal: Int,
    var canchasVisitante: Int,
    val idCampeonato: Int
)
