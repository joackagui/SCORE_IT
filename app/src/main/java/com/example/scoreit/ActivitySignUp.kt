package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scoreit.ActivityMenuPrincipal.Companion.ID_EMAIL
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.componentes.Usuario
import com.example.scoreit.database.AppDBAccess
import com.example.scoreit.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ActivitySignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth

    val dbAccess = applicationContext as AppDBAccess

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        binding.botonCrearCuenta.setOnClickListener {
            if (binding.usuarioSignUp.text.toString() == "" || binding.emailSignUp.text.toString() == ""
                || binding.passwordSignUp.text.toString() == "" || binding.passwordSafetySignUp.text.toString() == "") {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show()
            } else if (binding.passwordSignUp.text.toString() != binding.passwordSafetySignUp.text.toString()) {
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_LONG).show()
            } else if(binding.passwordSignUp.text.toString().length < 8){
                Toast.makeText(this, "Contraseña muy corta", Toast.LENGTH_LONG).show()
            } else {
                signUpUsuario()
            }
        }


    }

    fun signUpUsuario() {
        val email = binding.emailSignUp.text.toString()
        val password = binding.passwordSignUp.text.toString()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            val activityLogIn = Intent(this, ActivityLogIn::class.java)
            val nombreUsuario = binding.usuarioSignUp.text.toString()
            val nuevoUsuario = Usuario(email = email, nombreUsuario = nombreUsuario)
            dbAccess.room.usuarioDao().insertarUsuario(nuevoUsuario)
            Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_LONG).show()
            startActivity(activityLogIn)
        }.addOnFailureListener {
            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_LONG).show()
        }

    }
}