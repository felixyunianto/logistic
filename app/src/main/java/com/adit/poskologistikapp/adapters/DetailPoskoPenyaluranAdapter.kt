package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemDetailPoskoBinding
import com.adit.poskologistikapp.models.Penyaluran

class DetailPoskoPenyaluranAdapter (private var penyaluran : List<Penyaluran>) : RecyclerView.Adapter<DetailPoskoPenyaluranAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: ItemDetailPoskoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemDetailPoskoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvTanggal.text = penyaluran[position].tanggal
        holder.binding.tvNama.text = penyaluran[position].nama_produk
        holder.binding.tvJumlah.text = penyaluran[position].jumlah
    }

    override fun getItemCount() = penyaluran.size
}