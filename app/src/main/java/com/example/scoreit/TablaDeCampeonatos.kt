package com.example.scoreit

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TablaDeCampeonatos : AppCompatActivity() {

    private val teams = mutableListOf<TeamData>() // Lista de equipos dinámica
    private val matches = mutableListOf<Partido>() // Lista de partidos registrados
    private lateinit var tableLayout: TableLayout
    private lateinit var team1Spinner: Spinner
    private lateinit var team2Spinner: Spinner
    private lateinit var team1Score: EditText
    private lateinit var team2Score: EditText
    private lateinit var registerResultButton: Button
    private lateinit var messageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        tableLayout = findViewById(R.id.tableLayout)
        team1Spinner = findViewById(R.id.team1Spinner)
        team2Spinner = findViewById(R.id.team2Spinner)
        team1Score = findViewById(R.id.team1Score)
        team2Score = findViewById(R.id.team2Score)
        registerResultButton = findViewById(R.id.registerResultButton)
        messageTextView = findViewById(R.id.messageTextView)

        updateSpinners()

        // Configurar botón para registrar resultados
        registerResultButton.setOnClickListener {
            val team1 = team1Spinner.selectedItem as TeamData
            val team2 = team2Spinner.selectedItem as TeamData

            if (team1 == team2) {
                showMessage("No puedes registrar un partido entre el mismo equipo.")
                return@setOnClickListener
            }

            val score1 = team1Score.text.toString().toIntOrNull()
            val score2 = team2Score.text.toString().toIntOrNull()

            if (score1 == null || score2 == null) {
                showMessage("Por favor, ingresa los goles de ambos equipos.")
                return@setOnClickListener
            }

            // Crear y registrar partido
            val partido = Partido(
                jornada = matches.size + 1,
                equipoLocal = team1,
                equipoVisitante = team2,
                puntosLocal = score1,
                puntosVisitante = score2,
                tiempoRestante = null,
                tiempoLocal = null,
                tiempoVisitante = null,
                rondasLocal = null,
                rondasVisitante = null,
                idCampeonato = null
            )
            matches.add(partido)

            // Actualizar estadísticas
            updateStats(team1, team2, score1, score2)

            // Actualizar tabla
            refreshTable()

            // Limpiar campos
            team1Score.text.clear()
            team2Score.text.clear()
            messageTextView.visibility = View.GONE
        }
    }

    private fun updateStats(team1: TeamData, team2: TeamData, score1: Int, score2: Int) {
        team1.matchesPlayed++
        team2.matchesPlayed++

        when {
            score1 > score2 -> {
                team1.wins++
                team2.losses++
                team1.points += 3
            }
            score1 < score2 -> {
                team2.wins++
                team1.losses++
                team2.points += 3
            }
            else -> {
                team1.draws++
                team2.draws++
                team1.points++
                team2.points++
            }
        }
    }

    private fun refreshTable() {
        tableLayout.removeAllViews()
        for (team in teams) {
            addRowToTable(team)
        }
    }

    private fun updateSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teams)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        team1Spinner.adapter = adapter
        team2Spinner.adapter = adapter
    }

    private fun addRowToTable(team: TeamData) {
        val tableRow = TableRow(this)

        val nameTextView = TextView(this).apply {
            text = team.teamName
            setPadding(8, 8, 8, 8)
        }
        val matchesTextView = TextView(this).apply {
            text = team.matchesPlayed.toString()
            setPadding(8, 8, 8, 8)
        }
        val winsTextView = TextView(this).apply {
            text = team.wins.toString()
            setPadding(8, 8, 8, 8)
        }
        val drawsTextView = TextView(this).apply {
            text = team.draws.toString()
            setPadding(8, 8, 8, 8)
        }
        val lossesTextView = TextView(this).apply {
            text = team.losses.toString()
            setPadding(8, 8, 8, 8)
        }
        val pointsTextView = TextView(this).apply {
            text = team.points.toString()
            setPadding(8, 8, 8, 8)
        }

        tableRow.addView(nameTextView)
        tableRow.addView(matchesTextView)
        tableRow.addView(winsTextView)
        tableRow.addView(drawsTextView)
        tableRow.addView(lossesTextView)
        tableRow.addView(pointsTextView)

        tableLayout.addView(tableRow)
    }

    private fun showMessage(message: String) {
        messageTextView.text = message
        messageTextView.visibility = View.VISIBLE
    }
}

// Clase auxiliar para almacenar los datos de cada equipo
data class TeamData(
    val teamName: String,
    var matchesPlayed: Int,
    var wins: Int,
    var draws: Int,
    var losses: Int,
    var points: Int
)

// Clase auxiliar para registrar los partidos
data class Partido(
    val id: Int = 0,
    val jornada: Int,
    val equipoLocal: TeamData,
    val equipoVisitante: TeamData,
    var puntosLocal: Int?,
    var puntosVisitante: Int?,
    var tiempoRestante: Int?,
    var tiempoLocal: Int?,
    var tiempoVisitante: Int?,
    var rondasLocal: Int?,
    var rondasVisitante: Int?,
    val idCampeonato: Int?
)
