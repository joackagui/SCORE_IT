package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivityLogInBinding
import kotlinx.coroutines.launch

class ActivityLogIn : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var dbAccess: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = getDatabase(this)
        lifecycleScope.launch {
            login()
            cambiarASignUp()
        }
    }

    private fun cambiarASignUp() {
        binding.botonCambiarASignUp.setOnClickListener {
            val activitySignUp = Intent(this, ActivitySigningUp::class.java)
            startActivity(activitySignUp)
        }
    }

    private fun login() {
        lifecycleScope.launch {
            binding.botonLogIn.setOnClickListener {
                if (binding.emailLogIn.text.toString() == "" || binding.passwordLogIn.text.toString() == "") {
                    mensaje(1)
                } else if (binding.passwordLogIn.text.toString().length < 8) {
                    mensaje(2)
                } else {
                    logInUsuario()
                }
            }
        }
    }

    private fun logInUsuario() {
        lifecycleScope.launch {
            val usuario = dbAccess.usuarioDao().obternerPorEmail(binding.emailLogIn.text.toString())
            val email = binding.emailLogIn.text.toString()
            val password = binding.passwordLogIn.text.toString()
            if (usuario == null) {
                mensaje(3)
            } else if (usuario.email != email || usuario.password != password) {
                mensaje(4)
            } else {
                mensaje(5)
                cambioAMenuPrincipal()
            }
        }
    }

    private fun cambioAMenuPrincipal() {
        val intentMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
        intentMenuPrincipal.putExtra(USER_EMAIL, binding.emailLogIn.text.toString())
        insertarCampeonatos()
        startActivity(intentMenuPrincipal)
    }

    private fun insertarCampeonatos(){
        val listaCampeonatos: MutableList <Campeonato> = mutableListOf()
        lifecycleScope.launch {
            val cantidadDeCampeonatos = dbAccess.campeonatoDao().obtenerTodosLosCampeonatos().size
            if(cantidadDeCampeonatos == 0){
                for (i in 1..10){
                    val nuevoCampeonato = Campeonato(
                        nombreCampeonato = "Campeonato $i",
                        fechaDeInicio = "--:--:--",
                        seJuegaPorPuntosMaximos = false,
                        puntosParaGanar = 100,
                        seJuegaPorTiempoMaximo = false,
                        tiempoDeJuego = 45,
                        modoDeJuego = "Round Robin",
                        permisoDeDescanso = false,
                        tiempoDeDescanso = 1,
                        cantidadDeDescansos = 2,
                        permisoDeRonda = false,
                        cantidadDeRondas = 2,
                        idaYVuelta = false,
                        siempreUnGanador = true,
                        diferenciaDosPuntos = false
                    )
                    listaCampeonatos.add(nuevoCampeonato)
                }
                dbAccess.campeonatoDao().insertarVariosCampeonatos(listaCampeonatos)
            }
        }
    }

    private fun mensaje(numero: Int){
        when (numero) {
            1 -> {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show()
            }
            2 -> {
                Toast.makeText(this, "Contraseña muy corta", Toast.LENGTH_LONG).show()
            }
            3 -> {
                Toast.makeText(this, "Usuario no existente", Toast.LENGTH_LONG).show()
            }
            4 -> {
                Toast.makeText(this, "Email o Contraseña incorrectos", Toast.LENGTH_LONG).show()
            }
            5 -> {
                Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_LONG).show()
            }
        }
    }
}
