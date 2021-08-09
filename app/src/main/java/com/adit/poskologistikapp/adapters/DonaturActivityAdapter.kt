package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemDonaturBinding
import com.adit.poskologistikapp.models.Donatur

class DonaturActivityAdapter(private var donatur : List<Donatur>, private var listener : onClickDonaturAdapter) : RecyclerView.Adapter<DonaturActivityAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemDonaturBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemDonaturBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvNama.text = donatur[position].nama
        holder.binding.tvPoskoPenerima.text = donatur[position].posko_penerima
        holder.binding.tvJenisKebutuhan.text = donatur[position].jenis_kebutuhan
        holder.binding.tvKeterangan.text = donatur[position].keterangan
        holder.binding.tvAlamat.text = donatur[position].alamat
        holder.binding.tvTanggal.text = donatur[position].tanggal
        holder.binding.tvJumlah.text = donatur[position].jumlah
        holder.binding.tvSatuan.text = donatur[position].satuan

        holder.binding.ubah.setOnClickListener {
            listener.edit(donatur[position])
        }

        holder.binding.hapus.setOnClickListener {
            listener.delete(donatur[position])
        }
    }

    override fun getItemCount() = donatur.size
}

interface onClickDonaturAdapter {
    fun edit(donatur : Donatur)
    fun delete(donatur : Donatur)
}
