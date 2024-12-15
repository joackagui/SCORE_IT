package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(entity = Usuario::class,
        parentColumns = ["email"],
        childColumns = ["idUsuario"],
        onDelete = ForeignKey.CASCADE)
    ]
)

data class Campeonato(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var nombre: String,
    val fecha: String,
    val maxTiempo: Int,
    val maxCanchas: Int,
    val maxPuntos: Int,
    val dosPartidos: Boolean,
    val soloUnGanador: Boolean,
    val diferenciaDosPuntos: Boolean,
    val idUsuario: Int
)
