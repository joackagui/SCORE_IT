package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.scoreit.ActivityMenuPrincipal.Companion.ID_USUARIO
import com.example.scoreit.adapters.RecyclerCampeonatosCreados.RecyclerCampeonatosCreados
import com.example.scoreit.databinding.ActivityLogInBinding
import com.example.scoreit.databinding.ActivityMenuPrincipalBinding

class ActivityLogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.botonLogin.setOnClickListener {
            if (binding.password.text.toString() == binding.passwordSafety.text.toString() && binding.password.text.toString() != "") {
                val activityMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
                activityMenuPrincipal.putExtra(ID_USUARIO, binding.usuario.text.toString())
                startActivity(activityMenuPrincipal)
            }
        }
    }
}