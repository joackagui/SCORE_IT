package com.example.scoreit

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.database.AppDBAccess
import com.example.scoreit.databinding.ActivityCrearNuevoCampeonatoBinding
import java.util.Calendar

class ActivityCrearNuevoCampeonato : AppCompatActivity() {
    private lateinit var binding: ActivityCrearNuevoCampeonatoBinding

    private lateinit var datePickerDialog: DatePickerDialog

    val dbAccess = applicationContext as AppDBAccess

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearNuevoCampeonatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureSwitches()
        configureDatePicker()
        configureSpinner()
        configureCheckboxTiemposDeDescanso()
        configureCheckBoxCanchasRondas()
        configureAtrasButton()

        binding.botonGuardar.setOnClickListener{

        }

        binding.botonAtras.setOnClickListener{
            val intentVolverMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
            startActivity(intentVolverMenuPrincipal)
        }

    }

    //Configurar en checkbox de Tiempos de Descanso para que sus subopciones aparezcan o desaparezcan
    private fun configureCheckboxTiemposDeDescanso(){
        binding.checkboxTiemposDeDescaso.setOnCheckedChangeListener{_, isChecked ->
            val visibility = if(isChecked) View.VISIBLE else View.GONE
            binding.textViewMinutos.visibility = visibility
            binding.numberPickerMinutos.visibility = visibility
            binding.textViewCantidad.visibility = visibility
            binding.numberPickerCantidad.visibility = visibility
        }
    }

    private fun configureCheckBoxCanchasRondas(){
        binding.checkboxCanchasRondas.setOnCheckedChangeListener { _, isChecked ->
            val visibility = if(isChecked) View.VISIBLE else View.GONE
            binding.texViewCantidadParaGanar.visibility = visibility
            binding.numberPickerCantidadParaGanar.visibility = visibility
            binding.checkboxDiferenciaDosCanchas.visibility = visibility
        }
    }

    //boton de atras para volver al menu principal
    private fun configureAtrasButton(){
        binding.botonAtras.setOnClickListener {
            val intentVolverMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
            startActivity(intentVolverMenuPrincipal)
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
