package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemPoskoBinding
import com.adit.poskologistikapp.models.Posko

class PoskoActivityAdapter(private var posko : List<Posko>, private var listener: onClickPoskoAdapter) : RecyclerView.Adapter<PoskoActivityAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemPoskoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PoskoActivityAdapter.MyViewHolder {
        return MyViewHolder(ItemPoskoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PoskoActivityAdapter.MyViewHolder, position: Int) {
        holder.binding.tvNamaPosko.text = posko[position].nama
        holder.binding.tvJumlahPengungsi.text = posko[position].jumlah_pengungsi
        holder.binding.tvKontakHp.text = posko[position].kontak_hp
        holder.binding.tvLokasi.text = posko[position].lokasi
        holder.binding.hapus.setOnClickListener {
            listener.delete(posko[position])
        }

        holder.binding.edit.setOnClickListener {
            listener.edit(posko[position])
        }
    }

    override fun getItemCount() = posko.size
}

interface onClickPoskoAdapter {
    fun edit(posko : Posko)
    fun delete(posko : Posko)
}
