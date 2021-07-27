package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemPenerimaanBinding
import com.adit.poskologistikapp.models.Penerimaan

class PenerimaanAdapter(private var penerimaan : List<Penerimaan>) : RecyclerView.Adapter<PenerimaanAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemPenerimaanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPenerimaanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvJenisKebutuhan.text = penerimaan[position].jenis_kebutuhan
        holder.binding.tvKeterangan.text = penerimaan[position].keterangan
        holder.binding.tvJumlah.text = penerimaan[position].jumlah
        holder.binding.tvTanggal.text = penerimaan[position].tanggal
        holder.binding.tvStatus.text = penerimaan[position].status
    }

    override fun getItemCount() = penerimaan.size
}