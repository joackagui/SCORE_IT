package com.example.scoreit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scoreit.componentes.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM Usuario")
    suspend fun obtenerTodosLosUsuarios(): List<Usuario>?

    @Query("SELECT * FROM Usuario WHERE email = :email")
    suspend fun obternerPorEmail(email: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun obternerPorId(id: String): Usuario

    @Update
    suspend fun update(usuario: Usuario)

    @Insert
    suspend fun insert(usuario: Usuario)
}