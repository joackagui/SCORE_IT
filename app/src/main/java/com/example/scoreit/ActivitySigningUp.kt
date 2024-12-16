package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDBAccess
import com.example.scoreit.databinding.ActivitySigningUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActivitySigningUp : AppCompatActivity() {
    private lateinit var binding: ActivitySigningUpBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var dbAccess: AppDBAccess

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigningUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        dbAccess = applicationContext as AppDBAccess

        binding.botonCrearCuenta.setOnClickListener {
            if (binding.usuarioSignUp.text.toString() == "" || binding.emailSignUp.text.toString() == ""
                || binding.passwordSignUp.text.toString() == "" || binding.passwordSafetySignUp.text.toString() == "") {
                Toast.makeText(this, "@string/llenar_datos", Toast.LENGTH_LONG).show()
            } else if (binding.passwordSignUp.text.toString() != binding.passwordSafetySignUp.text.toString()) {
                Toast.makeText(this, "@string/contraseñas_no_coinciden", Toast.LENGTH_LONG).show()
            } else if(binding.passwordSignUp.text.toString().length < 8){
                Toast.makeText(this, "@string/contraseña_corta", Toast.LENGTH_LONG).show()
            } else {
                signUpUsuario()
            }
        }
    }

    private fun signUpUsuario() {
        val email = binding.emailSignUp.text.toString()
        val password = binding.passwordSignUp.text.toString()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            val activityLogIn = Intent(this, ActivityLogIn::class.java)
            val nombreUsuario = binding.usuarioSignUp.text.toString()
            val nuevoUsuario = Usuario(email = email, nombreUsuario = nombreUsuario)
            dbAccess.room.usuarioDao().insert(nuevoUsuario)
            Toast.makeText(this, "@string/usuario_creado", Toast.LENGTH_LONG).show()
            startActivity(activityLogIn)
        }.addOnFailureListener {
            Toast.makeText(this, "@string/error_al_crear_usuario", Toast.LENGTH_LONG).show()
        }

    }
}



