package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemProdukBinding
import com.adit.poskologistikapp.models.Logistik

class LogistikProdukActivityAdapter(private var logistik : List<Logistik>, private var listener : onClickLogistikAdapter) : RecyclerView.Adapter<LogistikProdukActivityAdapter.MyViewHolder>(){
    inner class MyViewHolder (val binding : ItemProdukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvNama.text = logistik[position].nama_produk
        holder.binding.tvJumlah.text = logistik[position].jumlah
        holder.binding.tvSatuan.text = logistik[position].satuan

        holder.binding.ubah.setOnClickListener {
            listener.edit(logistik[position])
        }

        holder.binding.hapus.setOnClickListener {
            listener.delete(logistik[position])
        }
    }

    override fun getItemCount() = logistik.size

}

interface onClickLogistikAdapter {
    fun edit(logistik : Logistik)
    fun delete(logistik : Logistik)
}
