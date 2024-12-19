package com.example.scoreit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
import com.example.scoreit.adapters.RecyclerEquiposPorCrear.RecyclerEquiposPorCrear
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.componentes.Partido
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.database.Converters
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
        val campeonatoid = intent.getStringExtra(ID_CAMPEONATO_DE).toString().toInt()
        binding.botonGuardarCambios.setOnClickListener{
            crearFixture(campeonatoid)
            cambiarAMenuPrincipal()
        }
    }

    private fun crearFixture(campeonatoid: Int) {
        lifecycleScope.launch {
            val campeonato = dbAccess.campeonatoDao().obtenerPorId(campeonatoid.toString())
            if(campeonato.modoDeJuego == "Round Robin"){
                roundRobin(campeonatoid)
            } else {
                llaves(campeonatoid)
            }
        }
    }

    private fun llaves(campeonatoid: Int) {
        lifecycleScope.launch {
            val equipos = dbAccess.equipoDao().obtenerEquiposPorIdCampeonato(campeonatoid.toString()).toMutableList()

            val numeroEquipos = equipos.size
            val siguientePotenciaDeDos =
                Integer.highestOneBit(numeroEquipos).takeIf { it == numeroEquipos }
                    ?: (Integer.highestOneBit(numeroEquipos) * 2)

            while (equipos.size < siguientePotenciaDeDos) {
                equipos.add(
                    Equipo(
                        id = -1,
                        nombre = "Equipo Default",
                        idCampeonato = campeonatoid
                    )
                )
            }

            val fases = mapOf(
                2 to "Final",
                4 to "Semifinal",
                8 to "Cuartos de Final",
                16 to "Octavos de Final",
                32 to "Dieciseisavos de Final"
            )

            var rondaEquipos = equipos
            var rondaActual = siguientePotenciaDeDos
            var jornada = 1

            val partidos = mutableListOf<Partido>()

            while (rondaActual > 1) {
                val nombreFase = fases[rondaActual] ?: "Fase $jornada"
                val nuevaRonda = mutableListOf<Equipo>()

                for (i in 0 until rondaEquipos.size / 2) {
                    val local = rondaEquipos[i]
                    val visitante = rondaEquipos[rondaEquipos.size - 1 - i]

                    val localGson = Converters().fromEquipo(local)
                    val visitanteGson = Converters().fromEquipo(visitante)

                    partidos.add(
                        Partido(
                            jornada = nombreFase,
                            primerEquipoJson = localGson,
                            segundoEquipoJson = visitanteGson,
                            puntosPrimerEquipo = 0,
                            puntosSegundoEquipo = 0,
                            rondasPrimerEquipo = "",
                            rondasSegundoEquipo = "",
                            porRondas = false,
                            idCampeonato = campeonatoid
                        )
                    )

                    nuevaRonda.add(if (local.id != -1) local else visitante)
                }

                rondaEquipos = nuevaRonda
                rondaActual /= 2
                jornada++
            }

            dbAccess.partidoDao().insertarPartidos(partidos)
        }
    }

    private fun roundRobin(idCampeonato: Int) {
        lifecycleScope.launch {
            val equipos = dbAccess.equipoDao().obtenerEquiposPorIdCampeonato(idCampeonato.toString())
            val partidos = mutableListOf<Partido>()
            val listaDeEquipos = equipos.toMutableList()

            val esImpar = listaDeEquipos.size % 2 != 0
            if (esImpar) {
                listaDeEquipos.add(Equipo(id = -1, nombre = "Descanso", idCampeonato = idCampeonato))
            }

            val totalFechas = listaDeEquipos.size - 1
            val mitad = listaDeEquipos.size / 2

            for (jornada in 1..totalFechas) {
                for (i in 0 until mitad) {
                    val local = listaDeEquipos[i]
                    val visitante = listaDeEquipos[listaDeEquipos.size - 1 - i]

                    if (local.id != -1 && visitante.id != -1) {
                        val campeonato = dbAccess.campeonatoDao().obtenerPorId(idCampeonato.toString())
                        val porRondas: Boolean = campeonato.permisoDeRonda

                        val localGson = Converters().fromEquipo(local)
                        val visitanteGson = Converters().fromEquipo(visitante)

                        partidos.add(
                            Partido(
                                jornada = jornada.toString(),
                                primerEquipoJson = localGson,
                                segundoEquipoJson = visitanteGson,
                                porRondas = porRondas,
                                idCampeonato = idCampeonato
                            )
                        )
                        if(campeonato.idaYVuelta){
                            val jornadaVuelta = totalFechas + 1 - jornada
                            partidos.add(
                                Partido(
                                    jornada = jornadaVuelta.toString(),
                                    primerEquipoJson = visitanteGson,
                                    segundoEquipoJson = localGson,
                                    porRondas = porRondas,
                                    idCampeonato = idCampeonato
                                )
                            )
                        }
                    }
                }

                val ultimo = listaDeEquipos.removeAt(listaDeEquipos.size - 1)
                listaDeEquipos.add(1, ultimo)
            }
            dbAccess.partidoDao().insertarPartidos(partidos)
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

}
