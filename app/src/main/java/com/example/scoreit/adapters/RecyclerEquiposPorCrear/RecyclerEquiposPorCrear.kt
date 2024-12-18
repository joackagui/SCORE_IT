package com.example.scoreit.adapters.RecyclerEquiposPorCrear

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.componentes.Partido
import com.example.scoreit.databinding.EnmarcadoEquipoBinding

class RecyclerEquiposPorCrear: RecyclerView.Adapter<RecyclerEquiposPorCrear.EquipoViewHolder>() {
    private val listaDatos = mutableListOf<Equipo>()

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        context = parent.context
        return EquipoViewHolder(EnmarcadoEquipoBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        holder.binding(listaDatos[position])
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class EquipoViewHolder(private val binding: EnmarcadoEquipoBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(equipo: Equipo) {

            //val primerEquipo = dbAccess.room.equipoDao().obtenerPorId(partido.primerEquipoId.toString())
            //val segundoEquipo = dbAccess.room.equipoDao().obtenerPorId(partido.segundoEquipoId.toString())

            //binding.nombreDelPrimerEquipo.text = primerEquipo.nombre


        }
    }

    fun addDataToList(list: MutableList<Equipo>) {
        listaDatos.clear()
        listaDatos.addAll(list)
        notifyDataSetChanged()
    }
}


