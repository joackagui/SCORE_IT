package com.example.scoreit

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.databinding.ActivityPanelArbitrajeBinding


class ActivityPanelArbitraje : AppCompatActivity() {
    private lateinit var binding: ActivityPanelArbitrajeBinding

    private lateinit var dbAccess: AppDataBase

    companion object{
        val ID_PARTIDO: String = "PARTIDO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPanelArbitrajeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        evaluacionDelPartido()
        aumentarPuntos()

        binding.botonDescansoSegundoEquipo.setOnClickListener {

        }

        binding.botonDescansoPrimerEquipo.setOnClickListener {
            if(dbAccess.partidoDao().obtenerSiPartidoPorPuntos("ID_PARTIDO")){

            }
        }
        //val input = dbAccess.campeonatoDao()

    }
    // funciones
//    private fun startTimer() {
//        // Verificar que haya un valor válido en el EditText
//        // val input = binding.input.text.toString()
////        if (input.isEmpty()) {
////            Toast.makeText(this, "Por favor ingresa un tiempo en minutos", Toast.LENGTH_SHORT).show()
////            return
//        }
//
//        // Convertir el valor ingresado en minutos a milisegundos
//        val minutes = input.toLong()
//        timeInMillis = minutes * 60 * 1000 // Convertir minutos a milisegundos
//
//        // Iniciar el temporizador
//        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                timeRemaining = millisUntilFinished
//                binding.timerTextView.text = formatTime(millisUntilFinished)
//            }
//
//            override fun onFinish() {
//                isRunning = false
//                binding.timerTextView.text = "00:00:00"
//                binding.buttonStart.text = "Start"
//                Toast.makeText(this@ActivityCountDownTimer, "¡Tiempo terminado!", Toast.LENGTH_SHORT).show()
//            }
//        }.start()
//
//        isRunning = true
//        binding.buttonStart.text = "Pause"
//    }
//
//    private fun pauseTimer() {
//        countDownTimer?.cancel()
//        isRunning = false
//        binding.buttonStart.text = "Resume"
//    }
//
//    private fun resetTimer() {
//        countDownTimer?.cancel()
//        isRunning = false
//        timeInMillis = 0L
//        timeRemaining = 0L
//        binding.timerTextView.text = "00:00:00"
//        binding.buttonStart.text = "Start"
//        binding.input.text.clear()
//    }
//
//    private fun formatTime(millis: Long): String {
//        val hours = millis / (1000 * 60 * 60)
//        val minutes = (millis / (1000 * 60)) % 60
//        val seconds = (millis / 1000) % 60
//        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
//    }



    private fun evaluacionDelPartido() {
        val partidoActual = dbAccess.partidoDao().obtenerPorId(intent.getStringExtra(ID_PARTIDO).toString())
        val maximoDePuntos = dbAccess.partidoDao().obtenerPuntosParaGanar(intent.getStringExtra(ID_PARTIDO).toString())
        val tieneTiempoDeJuego = dbAccess.partidoDao().obtenerSiPartidoPorTiempo(intent.getStringExtra(ID_PARTIDO).toString())
        val siempreGanador = dbAccess.partidoDao().obtenerSiempreUnGanador(intent.getStringExtra(ID_PARTIDO).toString())
        var cantidadDescansosPrimerEquipo = dbAccess.partidoDao().obtenerCantidadDescansos(intent.getStringExtra(ID_PARTIDO).toString())
        var cantidadDescansosSegundoEquipo = cantidadDescansosPrimerEquipo
        var diferenciaDeDosPuntos = dbAccess.partidoDao().obtenerSiHayDiferenciaDeDosPuntos(intent.getStringExtra(ID_PARTIDO).toString())

        /*if(partidoActual.puntosPrimerEquipo >= maximoDePuntos || partidoActual.puntosSegundoEquipo >= maximoDePuntos){
            finDelPartido()
            stopTimer()
        }
        if (tieneTiempoDeJuego){

        } else {
            if(siempreGanador){
                if(diferenciaDeDosPuntos){
                    finDelPartido()
                }
            } else {
                finDelPartido()
            }
        }

        if(partidoActual.tiempoDeJuegoRestante == 0){
            if(partidoActual.puntosPrimerEquipo != partidoActual.puntosSegundoEquipo){
                finDelPartido()
            } else if(!siempreGanador){
                finDelPartido()
            }
        }*/
    }

    private fun aumentarPuntos(){
        binding.botonPrimerEquipo.setOnClickListener {
            binding.puntajePrimerEquipo.text = (binding.puntajePrimerEquipo.text.toString().toInt() + 1).toString()
        }
        binding.botonSegundoEquipo.setOnClickListener {
            binding.puntajeSegundoEquipo.text = (binding.puntajeSegundoEquipo.text.toString().toInt() + 1).toString()
        }
    }

    /*private fun finDelPartido() {
        val nuevoPartido = Partido()
        dbAccess.room.partidoDao().update(nuevoPartido)
    }*/
}