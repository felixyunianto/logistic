package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemKebutuhanBinding
import com.adit.poskologistikapp.models.Kebutuhan

class KebutuhanAdapter(private var kebutuhan : List<Kebutuhan>, private var listener : onClickAdapterKebutuhan) : RecyclerView.Adapter<KebutuhanAdapter.MyViewHolder>() {
    inner class MyViewHolder (val binding : ItemKebutuhanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemKebutuhanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvJenisKebutuhan.text = kebutuhan[position].jenis_kebutuhan
        holder.binding.tvKeterangan.text = kebutuhan[position].keterangan
        holder.binding.tvJumlah.text = kebutuhan[position].jumlah.toString()
        holder.binding.tvTanggal.text = kebutuhan[position].tanggal
        holder.binding.tvPosko.text = kebutuhan[position].posko
        holder.binding.tvProduk.text = kebutuhan[position].produk
        holder.binding.btnEdit.setOnClickListener {
            listener.edit(kebutuhan[position])
        }

        holder.binding.btnHapus.setOnClickListener {
            listener.delete(kebutuhan[position])
        }
    }

    override fun getItemCount() = kebutuhan.size
}
interface onClickAdapterKebutuhan{
    fun edit(kebutuhan: Kebutuhan)
    fun delete(kebutuhan: Kebutuhan)
}