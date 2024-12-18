package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivitySigningUpBinding
import kotlinx.coroutines.launch

//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase

class ActivitySigningUp : AppCompatActivity() {
    private lateinit var binding: ActivitySigningUpBinding

//    private lateinit var auth: FirebaseAuth

    private lateinit var dbAccess: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigningUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = getDatabase(this)
        lifecycleScope.launch {
            botonPresionado()
        }


    }

    private fun botonPresionado(){
        binding.botonCrearCuenta.setOnClickListener {
            if (binding.usuarioSignUp.text.toString() == "" || binding.emailSignUp.text.toString() == ""
                || binding.passwordSignUp.text.toString() == "" || binding.passwordSafetySignUp.text.toString() == "") {
                mensaje(1)
            } else if (binding.passwordSignUp.text.toString() != binding.passwordSafetySignUp.text.toString()) {
                mensaje(3)
            } else if(binding.passwordSignUp.text.toString().length < 8){
                mensaje(2)
            } else {
                signUpUsuario()
            }
        }
    }

    private fun cambioALogin() {
        val activityLogIn = Intent(this, ActivityLogIn::class.java)
        startActivity(activityLogIn)
    }

    private fun signUpUsuario() {
        lifecycleScope.launch {
            val email = binding.emailSignUp.text.toString()
            val password = binding.passwordSignUp.text.toString()
            val nombreUsuario = binding.usuarioSignUp.text.toString()
            val nuevoUsuario =
                Usuario(email = email, nombreUsuario = nombreUsuario, password = password)

            dbAccess.usuarioDao().insert(nuevoUsuario)
            mensaje(4)
            cambioALogin()
        }
    }

    private fun mensaje(numero: Int){
        if(numero == 1){
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show()
        } else if(numero == 2){
            Toast.makeText(this, "Contraseña muy corta", Toast.LENGTH_LONG).show()
        } else if(numero == 3){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
        } else if(numero == 4){
            Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_LONG).show()
        }
    }
}



