package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Campeonato::class,
        parentColumns = ["id"],
        childColumns = ["idCampeonato"],
        onDelete = ForeignKey.CASCADE)
    ]
)

data class Partido(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jornada: Int,
    val primerEquipoId: Int,
    val segundoEquipoId: Int,
    var puntosPrimerEquipo: Int,
    var puntosSegundoEquipo: Int,
    var rondasPrimerEquipo: Int?,
    var rondasSegundoEquipo: Int?,
    val idCampeonato: Int
)
