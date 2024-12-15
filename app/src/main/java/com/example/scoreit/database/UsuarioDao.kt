package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM Usuario")
    suspend fun obtenerTodosLosUsuarios(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE email = :email")
    fun obternerPorEmail(email: String): Usuario

    @Update
    suspend fun update(usuario: Usuario)

    @Insert
    suspend fun insert(usuario: Usuario)

    @Insert
    fun insertarUsuario(usuario: Usuario)


}