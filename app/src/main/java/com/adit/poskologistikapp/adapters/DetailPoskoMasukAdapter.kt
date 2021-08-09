package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemDetailPoskoBinding
import com.adit.poskologistikapp.models.LogistikMasuk

class DetailPoskoMasukAdapter(private var masuk : List<LogistikMasuk>) : RecyclerView.Adapter<DetailPoskoMasukAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemDetailPoskoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemDetailPoskoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvJumlah.text = masuk[position].jumlah
        holder.binding.tvNama.text = masuk[position].pengirim
        holder.binding.tvTanggal.text = masuk[position].tanggal
    }

    override fun getItemCount() = masuk.size
}