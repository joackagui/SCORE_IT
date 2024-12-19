package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.io.Serializable

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
    val jornada: String,
    val primerEquipoJson: String,
    val segundoEquipoJson: String,
    var puntosPrimerEquipo: Int = 0,
    var puntosSegundoEquipo: Int = 0,
    var porRondas: Boolean,
    var rondasPrimerEquipo: String = "",
    var rondasSegundoEquipo: String = "",
    val idCampeonato: Int
): Serializable
