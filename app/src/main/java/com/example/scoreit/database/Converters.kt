package com.example.scoreit.database

import androidx.room.TypeConverter
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.componentes.Partido
import com.example.scoreit.componentes.Usuario
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromEquipo(equipo: Equipo?): String? = gson.toJson(equipo)

    @TypeConverter
    fun toEquipo(equipoString: String?): Equipo? =
        equipoString?.let { gson.fromJson(it, Equipo::class.java) }

    // Partido
    @TypeConverter
    fun fromPartido(partido: Partido?): String? = gson.toJson(partido)

    @TypeConverter
    fun toPartido(partidoString: String?): Partido? =
        partidoString?.let { gson.fromJson(it, Partido::class.java) }

    // Campeonato
    @TypeConverter
    fun fromCampeonato(campeonato: Campeonato?): String? = gson.toJson(campeonato)

    @TypeConverter
    fun toCampeonato(campeonatoString: String?): Campeonato? =
        campeonatoString?.let { gson.fromJson(it, Campeonato::class.java) }

    // Usuario
    @TypeConverter
    fun fromUsuario(usuario: Usuario?): String? = gson.toJson(usuario)

    @TypeConverter
    fun toUsuario(usuarioString: String?): Usuario? =
        usuarioString?.let { gson.fromJson(it, Usuario::class.java) }

    @TypeConverter
    fun fromList(value: List<String>?): String? = value?.joinToString(",")

    @TypeConverter
    fun toList(value: String?): List<String>? = value?.split(",") ?: emptyList()

    @TypeConverter
    fun fromIntList(value: List<Int>?): String? = value?.joinToString(",")

    @TypeConverter
    fun toIntList(value: String?): List<Int>? = value?.split(",")?.map { it.toInt() } ?: emptyList()

    @TypeConverter
    fun fromBoolean(value: Boolean): Int = if (value) 1 else 0

    @TypeConverter
    fun toBoolean(value: Int): Boolean = value == 1


    @TypeConverter
    fun fromBoolean(value: Boolean?): Int? = value?.let { if (it) 1 else 0 }

    @TypeConverter
    fun toBoolean(value: Int?): Boolean? = value?.let { it == 1 }

}
