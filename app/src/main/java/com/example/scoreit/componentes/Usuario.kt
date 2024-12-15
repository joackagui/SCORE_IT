package com.example.scoreit.componentes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey
    val email: String,
    val nombre: String
)
