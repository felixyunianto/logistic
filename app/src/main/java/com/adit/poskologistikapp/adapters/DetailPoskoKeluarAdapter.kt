package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemDetailPoskoBinding
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.LogistikMasuk

class DetailPoskoKeluarAdapter(private var keluar : List<LogistikKeluar>) : RecyclerView.Adapter<DetailPoskoKeluarAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemDetailPoskoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemDetailPoskoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvJumlah.text = keluar[position].jumlah
        holder.binding.tvNama.text = keluar[position].nama_produk
        holder.binding.tvTanggal.text = keluar[position].tanggal
    }

    override fun getItemCount() = keluar.size
}