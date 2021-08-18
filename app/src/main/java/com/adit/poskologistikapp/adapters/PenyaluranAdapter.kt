package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemPenyaluranBinding
import com.adit.poskologistikapp.models.Penyaluran

class PenyaluranAdapter(private var penyaluran : List<Penyaluran>, private var listener : onClickPenyaluranAdapter) : RecyclerView.Adapter<PenyaluranAdapter.MyViewHolder>() {
    inner class MyViewHolder (val binding : ItemPenyaluranBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPenyaluranBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvPenerima.text = penyaluran[position].penerima
        holder.binding.tvJenisKebutuhan.text = penyaluran[position].jenis_kebutuhan
        holder.binding.tvKeterangan.text = penyaluran[position].keterangan
        holder.binding.tvJumlah.text = penyaluran[position].jumlah
        holder.binding.tvTanggal.text = penyaluran[position].tanggal
        holder.binding.tvStatus.text = penyaluran[position].status

        holder.binding.btnEdit.setOnClickListener {
            listener.edit(penyaluran[position])
        }

        holder.binding.btnHapus.setOnClickListener {
            listener.delete(penyaluran[position])
        }

    }

    override fun getItemCount() = penyaluran.size
}

interface onClickPenyaluranAdapter{
    fun edit(penyaluran: Penyaluran)
    fun delete(penyaluran: Penyaluran)
}