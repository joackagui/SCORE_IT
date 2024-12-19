package com.example.scoreit.adapters.RecyclerEquiposPorCrear

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoreit.componentes.Equipo
import com.example.scoreit.databinding.EnmarcadoEquipoBinding

class RecyclerEquiposPorCrear: RecyclerView.Adapter<RecyclerEquiposPorCrear.EquipoViewHolder>() {
    private val listaDatos = mutableListOf<Equipo>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        context = parent.context
        return EquipoViewHolder(EnmarcadoEquipoBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = listaDatos[position]
        holder.binding(equipo)
    }

    override fun getItemCount(): Int = listaDatos.size

    inner class EquipoViewHolder(private val binding: EnmarcadoEquipoBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(equipo: Equipo) {
            binding.nombreEquipo.text = equipo.nombre

            binding.botonEntrarAlEquipo.setOnClickListener{
                //TODO: Ir al equipo
//                val activityEntrarAlEquipo = Intent(context, ActivityGogo::class.java)
//                activityEntrarAlEquipo.putExtra(EQUIPO_ID, equipo.id.toString())
//                context?.startActivity(activityEntrarAlEquipo)
            }
        }
    }

    fun addDataToList(list: MutableList<Equipo>) {
        listaDatos.clear()
        listaDatos.addAll(list)
    }
}


