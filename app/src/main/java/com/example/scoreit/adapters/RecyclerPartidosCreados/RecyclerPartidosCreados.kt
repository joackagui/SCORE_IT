package com.example.scoreit.adapters.RecyclerPartidosCreados

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Partido
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.database.AppDataBase.Companion.getDatabase
import com.example.scoreit.databinding.EnmarcadoPartidoBinding
import androidx.lifecycle.lifecycleScope
import com.example.scoreit.database.Converters

class RecyclerPartidosCreados:
    RecyclerView.Adapter<RecyclerPartidosCreados.PartidoViewHolder>() {

    private val listaDatos = mutableListOf<Partido>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidoViewHolder {
        context = parent.context

        return PartidoViewHolder(EnmarcadoPartidoBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: PartidoViewHolder, position: Int) {
        holder.binding(listaDatos[position])
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class PartidoViewHolder(private val binding: EnmarcadoPartidoBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(partido: Partido) {

            val primerEquipo = Converters().toEquipo(partido.primerEquipoJson)
            val segundoEquipo = Converters().toEquipo(partido.segundoEquipoJson)
            val primerEquipoNombre = primerEquipo.nombre
            val segundoEquipoNombre = segundoEquipo.nombre

            binding.jornadaDelPartido.text = "${binding.jornadaDelPartido.text} ${partido.jornada}"
            binding.nombreDelPrimerEquipo.text = primerEquipoNombre
            binding.nombreDelSegundoEquipo.text = segundoEquipoNombre
            binding.puntajeDelPrimerEquipo.text = partido.puntosPrimerEquipo.toString()
            binding.puntajeDelSegundoEquipo.text = partido.puntosSegundoEquipo.toString()

            if(partido.porRondas){
                binding.rondasDelPrimerEquipo.text = "(${partido.rondasPrimerEquipo})"
                binding.rondasDelSegundoEquipo.text = "(${partido.rondasSegundoEquipo})"
                binding.group.visibility = View.VISIBLE
            } else {
                binding.group.visibility = View.INVISIBLE
            }
        }
    }

    fun addDataToList(list: MutableList<Partido>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }

}