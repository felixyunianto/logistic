package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemPetugasBinding
import com.adit.poskologistikapp.models.Petugas

class PetugasActivityAdapter(private var petugas : List<Petugas>, private var listener : onClickPetugasAdapter) : RecyclerView.Adapter<PetugasActivityAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemPetugasBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPetugasBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvUsername.text = petugas[position].username
        holder.binding.tvLevel.text = petugas[position].level
        holder.binding.tvPosko.text = petugas[position].nama_posko
        holder.binding.ubah.setOnClickListener {
            listener.edit(petugas[position])
        }
        holder.binding.hapus.setOnClickListener {
            listener.delete(petugas[position])
        }

    }

    override fun getItemCount() = petugas.size
}

interface onClickPetugasAdapter {
    fun edit(petugas : Petugas)
    fun delete(petugas : Petugas)
}
