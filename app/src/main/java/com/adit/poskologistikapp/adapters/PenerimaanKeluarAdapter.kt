package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemKeluarTerimaBinding
import com.adit.poskologistikapp.models.LogistikKeluar

class PenerimaanKeluarAdapter(private var keluar : List<LogistikKeluar>, private var listener : onClickPenerimaanKeluarAdapter) : RecyclerView.Adapter<PenerimaanKeluarAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding : ItemKeluarTerimaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PenerimaanKeluarAdapter.MyViewHolder {
        return MyViewHolder(ItemKeluarTerimaBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: PenerimaanKeluarAdapter.MyViewHolder, position: Int) {
        holder.binding.tvJenisKebutuhan.text = keluar[position].jenis_kebutuhan
        holder.binding.tvJumlah.text = keluar[position].jumlah
        holder.binding.tvKeterangan.text = keluar[position].keterangan
        holder.binding.tvPoskoPenerima.text = keluar[position].penerima
        holder.binding.tvJenisKebutuhan.text = keluar[position].jenis_kebutuhan
        holder.binding.tvTanggal.text = keluar[position].tanggal

        holder.binding.btnKonfirmasi.setOnClickListener {
            listener.konfirmasi(keluar[position])
        }
    }

    override fun getItemCount() = keluar.size

}

interface onClickPenerimaanKeluarAdapter {
    fun konfirmasi(logistik: LogistikKeluar)
}
