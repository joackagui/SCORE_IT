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

            val primerEquipo = dbAccess.equipoDao().obtenerPorId(partido.primerEquipoId.toString())
            val segundoEquipo = dbAccess.equipoDao().obtenerPorId(partido.segundoEquipoId.toString())

            binding.nombreDelPrimerEquipo.text = primerEquipo.nombre
            binding.nombreDelSegundoEquipo.text = segundoEquipo.nombre
            binding.puntajeDelPrimerEquipo.text = partido.puntosPrimerEquipo.toString()
            binding.puntajeDelSegundoEquipo.text = partido.puntosSegundoEquipo.toString()
            binding.rondasDelPrimerEquipo.text = partido.rondasPrimerEquipo.toString()
            binding.rondasDelSegundoEquipo.text = partido.rondasSegundoEquipo.toString()

            if(dbAccess.partidoDao().obtenerSiHayRondas(partido.id.toString())){
                binding.group.visibility = View.INVISIBLE
            } else {
                binding.group.visibility = View.VISIBLE
            }
        }
    }

    fun addDataToList(list: MutableList<Partido>) {
        listaDatos.clear()
        listaDatos.addAll(list)
        notifyDataSetChanged()
    }

}