package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scoreit.ActivityMenuPrincipal.Companion.ID_USUARIO
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.databinding.ActivityLogInBinding
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

        binding.botonLogin.setOnClickListener {
            if(binding.usuario.text.toString() == "" || binding.email.text.toString() == "" || binding.password.text.toString() == "" || binding.passwordSafety.text.toString() == ""){
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else if (binding.password.text.toString() != binding.passwordSafety.text.toString()) {
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else if(binding.password.text.toString().length < 8){
                Toast.makeText(this, "Contraseña muy corta", Toast.LENGTH_SHORT).show()
            } else {
                val activityMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
                activityMenuPrincipal.putExtra(ID_USUARIO, binding.usuario.text.toString())
                startActivity(activityMenuPrincipal)
            }

        }
        /*binding.botonLogin.setOnClickListener{
            if(binding.usuario.text.toString() == "" || binding.email.text.toString() == "" || binding.password.text.toString() == "" || binding.passwordSafety.text.toString() == ""){
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else if (binding.password.text.toString() != binding.passwordSafety.text.toString()) {
                Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                loginUsuario()
            }
        }*/
    }

    fun loginUsuario(){
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        /*auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
            respuesta -> if(respuesta.isSuccessful){
                val activityMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
                activityMenuPrincipal.putExtra(ID_USUARIO, binding.usuario.text.toString())
                startActivity(activityMenuPrincipal)
            } else {
                Toast.makeText(this, "Error: ${respuesta.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }*/
        /*const result = await signInWithEmailAndPassword(auth, email, password)
        const provider = new GoogleAuthProvider()
        await linkWithRedirect(result.user, provider)
        const result = await getRedirectResult(auth)*/
    }
}