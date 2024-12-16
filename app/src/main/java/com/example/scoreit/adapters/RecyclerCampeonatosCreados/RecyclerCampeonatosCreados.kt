package com.example.scoreit.adapters.RecyclerCampeonatosCreados

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Campeonato
import com.example.scoreit.database.AppDBAccess
import com.example.scoreit.databinding.EnmarcadoCampeonatoBinding

class RecyclerCampeonatosCreados:
    RecyclerView.Adapter<RecyclerCampeonatosCreados.CampeonatoViewHolder>() {


        private lateinit var dbAccess: AppDBAccess

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

        inner class CampeonatoViewHolder(val binding: EnmarcadoCampeonatoBinding): RecyclerView.ViewHolder(binding.root) {
            fun binding(campeonato: Campeonato) {
                binding.nombreDelCampeonato.text = campeonato.nombreCampeonato
                binding.fechaDelCampeonato.text =campeonato.fechaDeInicio

                binding.botonEntrarAlCampeonato.setOnClickListener{
                    dbAccess.room.campeonatoDao().obtenerPorId(campeonato.id.toString())
                }
            }
        }

        fun addDataToList(list: MutableList<Campeonato>) {
            listaDatos.clear()
            listaDatos.addAll(list)
        }


}