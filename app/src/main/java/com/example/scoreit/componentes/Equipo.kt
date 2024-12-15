package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(entity = Partido::class,
        parentColumns = ["id"],
        childColumns = ["idPartido"],
        onDelete = ForeignKey.CASCADE)
    ]
)

data class Equipo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val listaDeJugadores: MutableList<String> = mutableListOf(),
    var puntos: Int = 0,
    var canchas: Int = 0,
    val idPartido: Int
    )
