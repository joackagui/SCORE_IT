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

data class Equipo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    var puntos: Int = 0,
    var rondasAFavor: Int = 0,
    var rondasEnContra: Int = 0,
    val partidosJugados: Int = 0,
    val idCampeonato: Int
    )
