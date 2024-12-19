package com.example.scoreit

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.scoreit.ActivityDefinirEquipos.Companion.ID_CAMPEONATO_DE
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivityCrearNuevoCampeonatoBinding
import kotlinx.coroutines.launch
import java.util.Calendar

class ActivityCrearNuevoCampeonato : AppCompatActivity() {

    private lateinit var binding: ActivityCrearNuevoCampeonatoBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dbAccess: AppDataBase

    companion object{
        const val ID_USER_NC: String = "USER_ID"
        const val USER_EMAIL_NC: String = "USER_EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearNuevoCampeonatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)

        configurarEncabezado()
        guardarYContinuar()
        volverMenuPrincipal()

        configureSwitches()
        configureDatePicker()
        configureSpinner()
        configureCheckboxTiemposDeDescanso()
        configureCheckBoxCanchasRondas()

    }

    private fun AppCompatActivity.configurarEncabezado() {
        val botonUsuario = findViewById<Button>(R.id.boton_de_usuario)
        val copaScoreIt = findViewById<ImageView>(R.id.copa_score_it)

        botonUsuario.setOnClickListener {
            val intent = Intent(this, ActivityLogIn::class.java)
            startActivity(intent)
        }

        copaScoreIt.setOnClickListener {
            val activityVolverMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
            activityVolverMenuPrincipal.putExtra(USER_EMAIL, intent.getStringExtra(USER_EMAIL_NC))
            startActivity(activityVolverMenuPrincipal)
        }
    }

    private fun guardarYContinuar() {
            binding.botonGuardar.setOnClickListener {
                lifecycleScope.launch {
                val nombreCampeonato = binding.nombreDelCampeonato.text.toString()
                val fechaDeInicio = binding.fechaDelCampeonato.text.toString()
                val seJuegaPorPuntosMaximos = binding.switchPuntaje.text.toString().toBoolean()
                var puntosParaGanar = 0
                val seJuegaPorTiempoMaximo = binding.switchTiempoDeJuego.text.toString().toBoolean()
                var tiempoDeJuego = 0
                val modoDeJuego = binding.spinnerModoJuego.toString()
                val permisoDeDescanso = binding.checkboxTiemposDeDescanso.text.toString().toBoolean()
                var tiempoDeDescanso = 0
                var cantidadDeDescansos = 0
                val permisoDeRonda = binding.checkboxRondas.text.toString().toBoolean()
                var cantidadDeRondas = 0
                val idaYVuelta = binding.checkboxIdaYVuelta.text.toString().toBoolean()
                val siempreUnGanador = binding.checkboxSiempreUnGanador.text.toString().toBoolean()
                val diferenciaDosPuntos = binding.checkboxDiferenciaDosPuntos.text.toString().toBoolean()
                val diferenciaDosRondas = binding.checkboxDiferenciaDosRondas.text.toString().toBoolean()

                if (binding.puntajeEditText.isEnabled) {
                    puntosParaGanar = binding.puntajeEditText.text.toString().toInt()
                }

                if (binding.tiempoDeJuegoEditText.isEnabled) {
                    tiempoDeJuego = binding.tiempoDeJuegoEditText.text.toString().toInt()
                }

                if (binding.numberPickerMinutosDeDescanso.isEnabled) {
                    tiempoDeDescanso = binding.numberPickerMinutosDeDescanso.value
                }

                if (binding.numberPickerCantidadDeDescansos.isEnabled) {
                    cantidadDeDescansos = binding.numberPickerCantidadDeDescansos.value
                }

                if (binding.numberPickerCantidadDeRondasParaGanar.isEnabled) {
                    cantidadDeRondas = binding.numberPickerCantidadDeRondasParaGanar.value
                }

                val nuevoCampeonato = Campeonato(
                        seleccionado = true,
                        nombreCampeonato = nombreCampeonato,
                        fechaDeInicio = fechaDeInicio,
                        seJuegaPorPuntosMaximos = seJuegaPorPuntosMaximos,
                        puntosParaGanar = puntosParaGanar,
                        seJuegaPorTiempoMaximo = seJuegaPorTiempoMaximo,
                        tiempoDeJuego = tiempoDeJuego,
                        modoDeJuego = modoDeJuego,
                        permisoDeDescanso = permisoDeDescanso,
                        tiempoDeDescanso = tiempoDeDescanso,
                        cantidadDeDescansos = cantidadDeDescansos,
                        permisoDeRonda = permisoDeRonda,
                        cantidadDeRondas = cantidadDeRondas,
                        idaYVuelta = idaYVuelta,
                        siempreUnGanador = siempreUnGanador,
                        diferenciaDosPuntos = diferenciaDosPuntos,
                        difenciaDeDosRondas = diferenciaDosRondas
                    )

                val idCampeonato = buscarCampeonatoLibre(nuevoCampeonato)
                if(idCampeonato != -1){
                    mensaje(true)
                    cambiarADefinirEquipos(idCampeonato)
                } else {
                    mensaje(false)
                }
            }
        }
    }

    private suspend fun buscarCampeonatoLibre(nuevoCampeonato: Campeonato): Int {
        val listaDeTodosLosCampeonatos = dbAccess.campeonatoDao().obtenerTodosLosCampeonatos()
        for (campeonato in listaDeTodosLosCampeonatos) {
            if (!campeonato.seleccionado) {
                nuevoCampeonato.id = campeonato.id
                dbAccess.campeonatoDao().update(nuevoCampeonato)
                return campeonato.id
            }
        }
        return -1
    }

    private fun mensaje(creadoExitosamente: Boolean) {
        if (creadoExitosamente) {
            Toast.makeText(this, "Nuevo campeonato registrado", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "No hay campeonatos disponibles", Toast.LENGTH_LONG).show()
        }
    }

    private fun cambiarADefinirEquipos(idCampeonato: Int) {
        val activityDefinirEquipos = Intent(this, ActivityDefinirEquipos::class.java)
        activityDefinirEquipos.putExtra(ID_CAMPEONATO_DE, idCampeonato.toString())
        activityDefinirEquipos.putExtra(USER_EMAIL, intent.getStringExtra(USER_EMAIL_NC))
        startActivity(activityDefinirEquipos)
    }

    private fun volverMenuPrincipal(){
        binding.botonAtras.setOnClickListener{
            val activityVolverMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
            activityVolverMenuPrincipal.putExtra(USER_EMAIL, intent.getStringExtra(USER_EMAIL_NC))
            startActivity(activityVolverMenuPrincipal)
        }
    }

    private fun configureCheckboxTiemposDeDescanso(){
        binding.checkboxTiemposDeDescanso.setOnCheckedChangeListener{_, isChecked ->
            val visibility = if(isChecked) View.VISIBLE else View.GONE
            binding.textViewMinutosDeDescanso.visibility = visibility
            binding.numberPickerMinutosDeDescanso.visibility = visibility
            binding.textViewCantidadDeDescansos.visibility = visibility
            binding.numberPickerCantidadDeDescansos.visibility = visibility
        }
    }

    private fun configureCheckBoxCanchasRondas(){
        binding.checkboxRondas.setOnCheckedChangeListener { _, isChecked ->
            val visibility = if(isChecked) View.VISIBLE else View.GONE
            binding.texViewCantidadDeRondasParaGanar.visibility = visibility
            binding.numberPickerCantidadDeRondasParaGanar.visibility = visibility
            binding.checkboxDiferenciaDosRondas.visibility = visibility
        }
    }

    // Configura los switches y sus comportamientos
    private fun configureSwitches() {
        binding.switchTiempoDeJuego.isUseMaterialThemeColors = false
        binding.switchPuntaje.isUseMaterialThemeColors = false

        binding.switchPuntaje.setOnCheckedChangeListener { _, isChecked ->
            binding.puntajeEditText.isEnabled = isChecked
        }

        binding.switchTiempoDeJuego.setOnCheckedChangeListener { _, isChecked ->
            binding.tiempoDeJuegoEditText.isEnabled = isChecked
        }
    }

    // Configura el DatePicker y el comportamiento del botón de fecha
    private fun configureDatePicker() {
        initDatePicker()
        binding.fechaDelCampeonato.text = getTodaysDate()

        binding.fechaDelCampeonato.setOnClickListener {
            openDatePicker()
        }
    }

    // Inicializa el DatePickerDialog con el estilo y comportamiento personalizados
    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val formattedMonth = month + 1
            val date = makeDateString(day, formattedMonth, year)
            binding.fechaDelCampeonato.text = date
        }

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val style = R.style.CustomDatePickerDialog
        datePickerDialog = DatePickerDialog(this, style, dateSetListener, year, month, day)
    }

    // Abre el DatePickerDialog
    private fun openDatePicker() {
        datePickerDialog.show()
    }

    // Obtiene la fecha actual formateada
    private fun getTodaysDate(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    // Crea una cadena de fecha formateada
    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return "${getMonthFormat(month)} $day $year"
    }

    // Devuelve la abreviatura del mes
    private fun getMonthFormat(month: Int): String {
        return when (month) {
            1 -> "JAN"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "APR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AUG"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> "JAN" // Caso por defecto
        }
    }

    // Configura el Spinner con estilo y datos personalizados
    private fun configureSpinner() {
        val modoDeJuego = resources.getStringArray(R.array.opciones_para_modo_de_juego)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_style, modoDeJuego)
        binding.spinnerModoJuego.adapter = adapter
    }

}
