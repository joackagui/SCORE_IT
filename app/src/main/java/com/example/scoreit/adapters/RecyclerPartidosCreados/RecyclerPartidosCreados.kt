package com.example.scoreit.adapters.RecyclerPartidosCreados

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Partido
import com.example.scoreit.database.AppDataBase
import com.example.scoreit.databinding.EnmarcadoPartidoBinding

class RecyclerPartidosCreados:
    RecyclerView.Adapter<RecyclerPartidosCreados.PartidoViewHolder>() {

    private lateinit var dbAccess: AppDataBase
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

            val primerEquipoID = partido.primerEquipoId
            val segundoEquipoID = partido.segundoEquipoId

            binding.nombreDelPrimerEquipo.text = primerEquipoID
            binding.nombreDelSegundoEquipo.text = segundoEquipoID
            binding.puntajeDelPrimerEquipo.text = partido.puntosPrimerEquipo
            binding.puntajeDelSegundoEquipo.text = partido.puntosSegundoEquipo

            if(partido.porRondas){
                binding.rondasDelPrimerEquipo.text = "(${partido.rondasPrimerEquipo.toString()})"
                binding.rondasDelSegundoEquipo.text = "(${partido.rondasSegundoEquipo.toString()})"
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