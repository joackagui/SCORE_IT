package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.ActivityMenuPrincipal.Companion.ID_EMAIL
import com.example.scoreit.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActivityLogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        login()
        cambiarASignUp()

    }

    private fun cambiarASignUp() {
        binding.botonCambiarASignUp.setOnClickListener {
            val activitySignUp = Intent(this, ActivitySigningUp::class.java)
            startActivity(activitySignUp)
        }
    }

    private fun login() {
        binding.botonLogIn.setOnClickListener {
            if (binding.emailLogIn.text.toString() == "" || binding.passwordLogIn.text.toString() == "") {
                Toast.makeText(this, "@string/llenar_datos", Toast.LENGTH_LONG).show()
            } else if(binding.passwordLogIn.text.toString().length < 8){
                Toast.makeText(this, "@string/contraseña_corta", Toast.LENGTH_LONG).show()
            } else {
                logInUsuario()
            }
        }
    }

    private fun logInUsuario() {
        val email = binding.emailLogIn.text.toString()
        val password = binding.passwordLogIn.text.toString()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { respuesta ->
            if (respuesta.isSuccessful) {
                val activityMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
                activityMenuPrincipal.putExtra(ID_EMAIL, binding.emailLogIn.text.toString())
                startActivity(activityMenuPrincipal)
            } else {
                Toast.makeText(this, "Email o Contraseña incorrectos", Toast.LENGTH_LONG).show()
                Toast.makeText(this, "Error: ${respuesta.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener(this){
            Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
        }
    }
}
