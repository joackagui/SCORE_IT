package com.example.scoreit.adapters.RecyclerCampeonatosCreados

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.databinding.EnmarcadoCampeonatoBinding

class RecyclerCampeonatosCreados:
    RecyclerView.Adapter<RecyclerCampeonatosCreados.CampeonatoViewHolder>() {

        private val listaDatos = mutableListOf<Campeonato>()
        private var context: Context? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampeonatoViewHolder {
            context = parent.context
            return CampeonatoViewHolder(EnmarcadoCampeonatoBinding.inflate(LayoutInflater.from(context), parent, false))
        }

        override fun onBindViewHolder(holder: CampeonatoViewHolder, position: Int) {
            holder.binding(listaDatos[position])
        }

        override fun getItemCount(): Int = listaDatos.size

        inner class CampeonatoViewHolder(private val binding: EnmarcadoCampeonatoBinding): RecyclerView.ViewHolder(binding.root) {
            fun binding(data: Campeonato) {
                binding.textViewEjemplo.text = data.nombre
            }
        }

        fun addDataToList(list: MutableList<Campeonato>) {
            listaDatos.clear()
            listaDatos.addAll(list)

        }

}