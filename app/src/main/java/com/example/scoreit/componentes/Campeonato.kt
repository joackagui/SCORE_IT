package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = Usuario::class,
        parentColumns = ["id"],
        childColumns = ["idUsuario"],
        onDelete = ForeignKey.CASCADE)
    ]
)

data class Campeonato(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val logo: String? = null,
    val nombreCampeonato: String,
    val fechaDeInicio: String,
    val seJuegaPorPuntosMaximos: Boolean,
    val puntosParaGanar: Int?,
    val seJuegaPorTiempoMaximo: Boolean,
    val tiempoDeJuego: Int?,
    val modoDeJuego: String,
    val permisoDeDescanso: Boolean,
    val tiempoDeDescanso: Int?,
    val cantidadDeDescansos: Int?,
    val permisoDeRonda: Boolean,
    val cantidadDeRondas: Int?,
    val idaYVuelta: Boolean,
    val siempreUnGanador: Boolean,
    val diferenciaDosPuntos: Boolean,
    val difenciaDeDosRondas: Boolean,
    val idUsuario: Int
): Serializable
