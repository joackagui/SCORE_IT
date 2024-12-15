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
    var nombreCampeonato: String,
    val fechaDeInicio: String,
    val puntosParaGanar: Int?,
    val tiempoDeJuego: Int?,
    val modoDeJuego: String,
    val tiempoDeDescanso: Int?,
    val cantidadDeDescansos: Int?,
    val cantidadDeRondas: Int?,
    val idaYVuelta: Boolean,
    val siempreUnGanador: Boolean,
    val diferenciaDosPuntos: Boolean,
    val difenciaDeDosRondas: Boolean,
    val idUsuario: Int
)
