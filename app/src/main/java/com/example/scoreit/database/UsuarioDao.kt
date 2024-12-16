package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM Usuario")
    fun obtenerTodosLosUsuarios(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE email = :email")
    fun obternerPorEmail(email: String): Usuario

    @Update
    fun update(usuario: Usuario)

    @Insert
    fun insert(usuario: Usuario)
}