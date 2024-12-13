package com.example.scoreit.adapters.RecyclerPartidosCreados

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.databinding.EnmarcadoCampeonatoBinding
import com.example.scoreit.databinding.EnmarcadoPartidoBinding

class RecyclerPartidosCreados: RecyclerView.Adapter<RecyclerPartidosCreados.PartidoViewHolder>() {
    private val listaDatos = mutableListOf<Equipo>()
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
        fun binding(data: Equipo) {

        }
    }

    fun addDataToList(list: MutableList<Equipo>) {
        listaDatos.clear()
        listaDatos.addAll(list)

    }

}