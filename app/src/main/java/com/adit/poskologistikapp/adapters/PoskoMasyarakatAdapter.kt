package com.adit.poskologistikapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemPoskoMasyarakatBinding
import com.adit.poskologistikapp.models.Posko

class PoskoMasyarakatAdapter(private var posko : List<Posko>, private var listener : onClickAdapter) : RecyclerView.Adapter<PoskoMasyarakatAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemPoskoMasyarakatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(ItemPoskoMasyarakatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvNamaPosko.text = posko[position].nama
        holder.itemView.setOnClickListener {
            listener.donatur(posko[position])
        }
    }

    override fun getItemCount() = posko.size
}

interface onClickAdapter{
    fun donatur(posko: Posko)
}