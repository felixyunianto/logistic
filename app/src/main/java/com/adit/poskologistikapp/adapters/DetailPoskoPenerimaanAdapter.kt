package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemDetailPoskoBinding
import com.adit.poskologistikapp.models.Penerimaan

class DetailPoskoPenerimaanAdapter(private var penerimaan : List<Penerimaan>) : RecyclerView.Adapter<DetailPoskoPenerimaanAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: ItemDetailPoskoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemDetailPoskoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvTanggal.text = penerimaan[position].tanggal
        holder.binding.tvNama.text = penerimaan[position].nama_produk
        holder.binding.tvJumlah.text = penerimaan[position].jumlah
    }

    override fun getItemCount() = penerimaan.size
}