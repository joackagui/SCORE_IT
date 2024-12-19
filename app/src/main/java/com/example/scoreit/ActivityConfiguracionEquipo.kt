package com.example.scoreit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class AddTeamActivity : AppCompatActivity() {

    private lateinit var btnAddPlayers: Button
    private lateinit var btnAddLogo: ImageButton
    private lateinit var playerListContainer: LinearLayout
    private lateinit var etTeamName: EditText
    private var playerCount = 0
    private var logoUri: Uri? = null

    // Launcher para abrir la galería
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                logoUri = data?.data
                if (logoUri != null) {
                    btnAddLogo.setImageURI(logoUri) // Mostrar la imagen seleccionada
                }
            } else {
                Toast.makeText(this, "Selección cancelada", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion_equipo)

        btnAddPlayers = findViewById(R.id.btnAddPlayers)
        playerListContainer = findViewById(R.id.playerListContainer)
        etTeamName = findViewById(R.id.etTeamName)
        btnAddLogo = findViewById(R.id.btnAddLogo)

        // Botón para añadir jugadores
        btnAddPlayers.setOnClickListener {
            addPlayerToList()
        }

        // Botón para seleccionar el logo del equipo
        btnAddLogo.setOnClickListener {
            openGallery()
        }

        // Botón para volver atrás
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Botón para guardar equipo
        findViewById<Button>(R.id.btnSave).setOnClickListener {
            saveTeam()
        }
    }

    private fun addPlayerToList() {
        playerCount++

        val playerName = "Jugador $playerCount"

        // Crear un TextView para mostrar el jugador
        val playerTextView = TextView(this)
        playerTextView.text = playerName
        playerTextView.textSize = 16f
        playerTextView.setTextColor(resources.getColor(android.R.color.white, null))
        playerTextView.setPadding(8, 8, 8, 8)

        // Añadir borde inferior
        playerTextView.setBackgroundResource(R.drawable.player_list_item_border)

        playerListContainer.addView(playerTextView)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        pickImageLauncher.launch(intent)
    }

    private fun saveTeam() {
        val teamName = etTeamName.text.toString()

        if (teamName.isBlank()) {
            etTeamName.error = "El nombre del equipo es obligatorio"
            return
        }

        // Lógica para guardar el equipo
        Toast.makeText(this, "Equipo '$teamName' guardado", Toast.LENGTH_SHORT).show()
        finish()
    }
}

