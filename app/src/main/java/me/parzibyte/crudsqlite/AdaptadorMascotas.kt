package me.parzibyte.crudsqlite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.parzibyte.crudsqlite.AdaptadorMascotas.MyViewHolder
import me.parzibyte.crudsqlite.databinding.FilaMascotaBinding
import me.parzibyte.crudsqlite.modelos.Mascota

class AdaptadorMascotas(private var listaDeMascotas: List<Mascota>) :
    RecyclerView.Adapter<MyViewHolder>() {
    fun setListaDeMascotas(listaDeMascotas: List<Mascota>) {
        this.listaDeMascotas = listaDeMascotas
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val filaMascota =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.fila_mascota, viewGroup, false)
        return MyViewHolder(filaMascota)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        // Obtener la mascota de nuestra lista gracias al índice i
        val mascota = listaDeMascotas[i]

        // Obtener los datos de la lista
        val nombreMascota = mascota.nombre
        val edadMascota = mascota.edad
        // Y poner a los TextView los datos con setText
        myViewHolder.binding.tvNombre.text = nombreMascota
        myViewHolder.binding.tvEdad.text = edadMascota.toString()
    }

    override fun getItemCount(): Int {
        return listaDeMascotas.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = FilaMascotaBinding.bind(itemView)
        // var nombre: TextView
        // var edad: TextView

        // init {
            // nombre = itemView.findViewById(R.id.tvNombre)
            // edad = itemView.findViewById(R.id.tvEdad)
        // }
    }
}