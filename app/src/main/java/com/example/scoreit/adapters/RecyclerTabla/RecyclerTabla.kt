package com.example.scoreit.adapters.RecyclerTabla

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.databinding.TablaFilaBinding

class RecyclerTabla: RecyclerView.Adapter<RecyclerTabla.TablaViewHolder>(){
    private val listaDatos = mutableListOf<Equipo>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablaViewHolder {
        context = parent.context

        return TablaViewHolder(TablaFilaBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: TablaViewHolder, position: Int) {
        holder.binding(listaDatos[position])
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class TablaViewHolder(private val binding: TablaFilaBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(equipo: Equipo) {
            binding.nombreEquipoTabla.text = equipo.nombre
            binding.puntosInGameTabla.text = equipo.puntosInGame.toString()
            binding.puntosFinalesTabla.text = equipo.puntosFinales.toString()
            binding.partidosJugadosTabla.text = equipo.partidosJugados.toString()
            binding.partidosGanadosTabla.text = equipo.partidosGanados.toString()

        }
    }
    fun addDataToList(list: MutableList<Equipo>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }
}
