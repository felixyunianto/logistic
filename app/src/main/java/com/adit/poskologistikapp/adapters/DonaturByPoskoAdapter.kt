package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemDonaturByPoskoBinding
import com.adit.poskologistikapp.models.Donatur

class DonaturByPoskoAdapter(private var donatur : List<Donatur>) : RecyclerView.Adapter<DonaturByPoskoAdapter.MyViewHolder>() {
    class MyViewHolder (val binding : ItemDonaturByPoskoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DonaturByPoskoAdapter.MyViewHolder {
        return MyViewHolder(ItemDonaturByPoskoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DonaturByPoskoAdapter.MyViewHolder, position: Int) {
        holder.binding.tvNama.text = donatur[position].nama
        holder.binding.tvPoskoPenerima.text = donatur[position].posko_penerima
        holder.binding.tvJenisKebutuhan.text = donatur[position].jenis_kebutuhan
        holder.binding.tvKeterangan.text = donatur[position].keterangan
        holder.binding.tvAlamat.text = donatur[position].alamat
        holder.binding.tvTanggal.text = donatur[position].tanggal
    }

    override fun getItemCount() = donatur.size
}