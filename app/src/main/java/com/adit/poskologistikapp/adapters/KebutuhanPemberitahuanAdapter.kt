package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemKebutuhanPemberitahuanBinding
import com.adit.poskologistikapp.models.Kebutuhan

class KebutuhanPemberitahuanAdapter(private var kebutuhan : List<Kebutuhan>) : RecyclerView.Adapter<KebutuhanPemberitahuanAdapter.MyViewHolder>() {
    inner class MyViewHolder (val binding : ItemKebutuhanPemberitahuanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemKebutuhanPemberitahuanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvJenisKebutuhan.text = kebutuhan[position].jenis_kebutuhan
        holder.binding.tvJumlah.text = kebutuhan[position].jumlah.toString()
        holder.binding.tvTanggal.text = kebutuhan[position].tanggal
        holder.binding.tvKeterangan.text = kebutuhan[position].keterangan
        holder.binding.tvPosko.text = kebutuhan[position].posko
        holder.binding.tvProduk.text = kebutuhan[position].produk
    }

    override fun getItemCount() = kebutuhan.size
}