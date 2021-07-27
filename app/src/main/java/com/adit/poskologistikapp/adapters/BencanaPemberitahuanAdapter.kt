package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adit.poskologistikapp.databinding.ItemBencanaPemberitahuanBinding
import com.adit.poskologistikapp.models.Bencana

class BencanaPemberitahuanAdapter(private var bencana : List<Bencana>): RecyclerView.Adapter<BencanaPemberitahuanAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemBencanaPemberitahuanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemBencanaPemberitahuanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.ivFoto.load(bencana[position].foto)
        holder.binding.tvDetail.text = bencana[position].detail
        holder.binding.tvNamaBencana.text = bencana[position].nama
        holder.binding.tvTanggal.text = bencana[position].tanggal
    }

    override fun getItemCount() = bencana.size

}