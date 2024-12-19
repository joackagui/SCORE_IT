package com.example.scoreit.adapters.RecyclerCampeonatosCreados

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.ActivityDentroDelCampeonato
import com.example.scoreit.ActivityDentroDelCampeonato.Companion.ID_CAMPEONATO_DC
import com.example.scoreit.ActivityMenuPrincipal.Companion.USER_EMAIL
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
        val campeonato = listaDatos[position]
        holder.binding(campeonato)
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class CampeonatoViewHolder(val binding: EnmarcadoCampeonatoBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(campeonato: Campeonato) {
            binding.botonEntrarAlCampeonato.setOnClickListener{
                val activityDentroDelCampeonato = Intent(context, ActivityDentroDelCampeonato::class.java)
                activityDentroDelCampeonato.putExtra(ID_CAMPEONATO_DC, campeonato.id.toString())
                context?.startActivity(activityDentroDelCampeonato)
            }
            binding.nombreDelCampeonato.text = campeonato.nombreCampeonato
            binding.fechaDelCampeonato.text = campeonato.fechaDeInicio
        }
    }

    fun addDataToList(list: MutableList<Campeonato>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }


}