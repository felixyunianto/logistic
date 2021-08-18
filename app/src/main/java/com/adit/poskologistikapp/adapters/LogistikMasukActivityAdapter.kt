package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adit.poskologistikapp.databinding.ItemLogistikMasukBinding
import com.adit.poskologistikapp.models.LogistikMasuk

class LogistikMasukActivityAdapter(private var logistik_masuk : List<LogistikMasuk>, private var listener : onClickMasukAdapter) : RecyclerView.Adapter<LogistikMasukActivityAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemLogistikMasukBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemLogistikMasukBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvPengirim.text = logistik_masuk[position].pengirim
        holder.binding.tvPoskoPenerima.text = "Posko BPBD Brebes"
        holder.binding.tvJenisKebutuhan.text = logistik_masuk[position].jenis_kebutuhan
        holder.binding.tvKeterangan.text = logistik_masuk[position].keterangan
        holder.binding.tvJumlah.text = logistik_masuk[position].jumlah
        holder.binding.tvTanggal.text = logistik_masuk[position].tanggal
        holder.binding.tvStatus.text = logistik_masuk[position].status
        holder.binding.tvSatuan.text = logistik_masuk[position].satuan
        holder.binding.ivFoto.load(logistik_masuk[position].foto)

        holder.binding.btnEdit.setOnClickListener {
            listener.edit(logistik_masuk[position])
        }

        holder.binding.btnHapus.setOnClickListener {
            listener.delete(logistik_masuk[position])
        }
    }

    override fun getItemCount() = logistik_masuk.size
}

interface onClickMasukAdapter {
    fun edit(logistik_masuk : LogistikMasuk)
    fun delete(logistik_masuk : LogistikMasuk)
}
