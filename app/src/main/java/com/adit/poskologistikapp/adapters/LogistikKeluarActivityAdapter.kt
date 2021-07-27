package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemLogistikKeluarBinding
import com.adit.poskologistikapp.models.LogistikKeluar

class LogistikKeluarActivityAdapter(private var logistik_keluar : List<LogistikKeluar>, private var listener : onClickKeluarAdapter) : RecyclerView.Adapter<LogistikKeluarActivityAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding : ItemLogistikKeluarBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LogistikKeluarActivityAdapter.MyViewHolder {
        return MyViewHolder(ItemLogistikKeluarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: LogistikKeluarActivityAdapter.MyViewHolder,
        position: Int
    ) {
        holder.binding.tvPoskoPenerima.text = logistik_keluar[position].penerima
        holder.binding.tvJenisKebutuhan.text = logistik_keluar[position].jenis_kebutuhan
        holder.binding.tvKeterangan.text = logistik_keluar[position].keterangan
        holder.binding.tvJumlah.text = logistik_keluar[position].jumlah
        holder.binding.tvTanggal.text = logistik_keluar[position].tanggal
        holder.binding.tvStatus.text = logistik_keluar[position].status

//        holder.binding.btnEdit.setOnClickListener {
//            listener.edit(logistik_keluar[position])
//        }
//
//        holder.binding.btnHapus.setOnClickListener {
//            listener.delete(logistik_keluar[position])
//        }
    }

    override fun getItemCount() = logistik_keluar.size
}

interface onClickKeluarAdapter {
    fun edit(logistik_keluar : LogistikKeluar)
    fun delete(logistik_keluar : LogistikKeluar)
}
