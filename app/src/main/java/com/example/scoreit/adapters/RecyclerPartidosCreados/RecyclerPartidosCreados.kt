package com.example.scoreit.adapters.RecyclerPartidosCreados

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.componentes.Partido
import com.example.scoreit.database.AppDBAccess
import com.example.scoreit.databinding.EnmarcadoPartidoBinding

class RecyclerPartidosCreados:
    RecyclerView.Adapter<RecyclerPartidosCreados.PartidoViewHolder>() {

    private lateinit var dbAccess: AppDBAccess

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
            binding.nombreDelPrimerEquipo.text = partido.equipoLocal.nombre
            binding.nombreDelSegundoEquipo.text = partido.equipoVisitante.nombre
            binding.puntajeDelPrimerEquipo.text = partido.puntosLocal.toString()
            binding.puntajeDelSegundoEquipo.text = partido.puntosVisitante.toString()
            binding.rondasDelPrimerEquipo.text = partido.rondasLocal.toString()
            binding.rondasDelSegundoEquipo.text = partido.rondasVisitante.toString()
        }
    }

    fun addDataToList(list: MutableList<Partido>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }

}