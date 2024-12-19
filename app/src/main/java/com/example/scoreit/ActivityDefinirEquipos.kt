package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
import com.example.scoreit.adapters.RecyclerEquiposPorCrear.RecyclerEquiposPorCrear
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.componentes.Partido
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.ActivityDefinirEquiposBinding
import kotlinx.coroutines.launch

class ActivityDefinirEquipos : AppCompatActivity() {

    private val recyclerEquiposCreados: RecyclerEquiposPorCrear by lazy {RecyclerEquiposPorCrear()}
    private lateinit var binding: ActivityDefinirEquiposBinding
    private lateinit var dbAccess: AppDataBase

    private var num = 3

    companion object{
        const val ID_CAMPEONATO_DE: String = "CAMPEONATO"
        const val USER_EMAIL_DE: String = "EMAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDefinirEquiposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)

        configurarEncabezado()
        equiposPredeterminados()

        botonPresionado()

        setUpRecyclerView()
        guardarCambios()
    }

    private fun AppCompatActivity.configurarEncabezado() {
        val botonUsuario = findViewById<Button>(R.id.boton_de_usuario)
        val copaScoreIt = findViewById<ImageView>(R.id.copa_score_it)

        botonUsuario.setOnClickListener {
            resetCampeonato()
            val intent = Intent(this, ActivityLogIn::class.java)
            startActivity(intent)
        }

        copaScoreIt.setOnClickListener {
            resetCampeonato()
            val activityVolverMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
            activityVolverMenuPrincipal.putExtra(USER_EMAIL, intent.getStringExtra(USER_EMAIL_DE))
            startActivity(activityVolverMenuPrincipal)
        }
    }

    private fun resetCampeonato() {
        lifecycleScope.launch {
            val campeonatoid = intent.getStringExtra(ID_CAMPEONATO_DE)
            if(campeonatoid != null){
                val campeonatoPorResetear = dbAccess.campeonatoDao().obtenerPorId(campeonatoid)
                campeonatoPorResetear.nombreCampeonato = "Campeonato $campeonatoid"
                campeonatoPorResetear.seleccionado = false
                campeonatoPorResetear.fechaDeInicio = "--:--:--"
                campeonatoPorResetear.seJuegaPorPuntosMaximos = false
                campeonatoPorResetear.puntosParaGanar = 100
                campeonatoPorResetear.seJuegaPorTiempoMaximo = false
                campeonatoPorResetear.tiempoDeJuego = 45
                campeonatoPorResetear.modoDeJuego = "Round Robin"
                campeonatoPorResetear.permisoDeDescanso = false
                campeonatoPorResetear.tiempoDeDescanso = 1
                campeonatoPorResetear.cantidadDeDescansos = 2
                campeonatoPorResetear.permisoDeRonda = false
                campeonatoPorResetear.cantidadDeRondas = 2
                campeonatoPorResetear.idaYVuelta = false
                campeonatoPorResetear.siempreUnGanador = true
                campeonatoPorResetear.diferenciaDosPuntos = false
                campeonatoPorResetear.difenciaDeDosRondas = false

                dbAccess.campeonatoDao().update(campeonatoPorResetear)
            }
        }
    }

    private fun equiposPredeterminados() {
        val campeonatoid = intent.getStringExtra(ID_CAMPEONATO_DE)
        if(campeonatoid != null){
            lifecycleScope.launch {
                val dosEquipos: MutableList<Equipo> = mutableListOf()
                val primerEquipo = Equipo(nombre = "Equipo 1", idCampeonato = campeonatoid.toInt())
                val segundoEquipo = Equipo(nombre = "Equipo 2", idCampeonato = campeonatoid.toInt())
                dbAccess.equipoDao().insert(primerEquipo)
                dbAccess.equipoDao().insert(segundoEquipo)
                dosEquipos.add(primerEquipo)
                dosEquipos.add(segundoEquipo)
                recyclerEquiposCreados.addDataToList(dosEquipos)
                setUpRecyclerView()
            }
        }
    }

    private fun botonPresionado(){
        binding.botonAnadirEquipo.setOnClickListener {
            agregarNuevoEquipo()
        }
    }

    private fun guardarCambios(){
        binding.botonGuardarCambios.setOnClickListener{
            crearFixture()
            cambiarAMenuPrincipal()
        }
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch {
            val listaDeEquipos = dbAccess.equipoDao().obtenerEquiposPorIdCampeonato(intent.getStringExtra(ID_CAMPEONATO_DE).toString())
            recyclerEquiposCreados.addDataToList(listaDeEquipos)
        }
        binding.recyclerCantidadDeEquipos.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerEquiposCreados
        }
    }

    private fun agregarNuevoEquipo() {
        val campeonatoid = intent.getStringExtra(ID_CAMPEONATO_DE)
        if(campeonatoid != null){
            lifecycleScope.launch {
                val nuevoEquipo = Equipo(nombre = "Equipo $num", idCampeonato = campeonatoid.toInt())
                num += 1
                dbAccess.equipoDao().insert(nuevoEquipo)
                setUpRecyclerView()
            }
        }
    }

    private fun cambiarAMenuPrincipal() {
        val activityMenuPrincipal = Intent(this, ActivityMenuPrincipal::class.java)
        activityMenuPrincipal.putExtra(USER_EMAIL, intent.getStringExtra(USER_EMAIL_DE))
        startActivity(activityMenuPrincipal)
    }

    private fun crearFixture() {
        val campeonatoid = intent.getStringExtra(ID_CAMPEONATO_DE)
        if(campeonatoid != null){
            lifecycleScope.launch {
                val listaDeEquipos = dbAccess.equipoDao().obtenerEquiposPorIdCampeonato(campeonatoid)
                val campeonato = dbAccess.campeonatoDao().obtenerPorId(campeonatoid)
                val listaDeEquiposId = mutableListOf<String>()
                val partidos = mutableListOf<Partido>()

                for (i in 0..<listaDeEquipos.size) {
                    listaDeEquiposId.add(listaDeEquipos[i].id.toString())
                }

                if (listaDeEquipos.size < 2) throw IllegalArgumentException("Se necesitan al menos 2 equipos")

                val esImpar = listaDeEquipos.size % 2 != 0

                if (esImpar) listaDeEquiposId.add("Descanso")

                val totalFechas = listaDeEquiposId.size - 1

                for (jornada in 1..totalFechas) {
                    for (i in 0 until listaDeEquiposId.size / 2) {
                        val local = listaDeEquiposId[i]
                        val visitante = listaDeEquiposId[listaDeEquiposId.size - 1 - i]

                        if (local != "Descanso" && visitante != "Descanso") {
                            partidos.add(
                                Partido(
                                    jornada = jornada,
                                    primerEquipoId = dbAccess.equipoDao().obtenerPorId(local).id.toString(),
                                    segundoEquipoId = dbAccess.equipoDao().obtenerPorId(visitante).id.toString(),
                                    puntosPrimerEquipo = "0",
                                    puntosSegundoEquipo = "0",
                                    rondasPrimerEquipo = "0",
                                    rondasSegundoEquipo = "0",
                                    porRondas = campeonato.permisoDeRonda,
                                    idCampeonato = campeonatoid.toInt()
                                )
                            )
                        }
                    }
                    val ultimo = listaDeEquipos.removeAt(listaDeEquipos.size - 1)
                    listaDeEquipos.add(1, ultimo)
                }
                dbAccess.partidoDao().insertarPartidos(partidos)
            }
        }
    }
}
