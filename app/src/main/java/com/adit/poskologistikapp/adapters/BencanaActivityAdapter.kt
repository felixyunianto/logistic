package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.adit.poskologistikapp.databinding.ItemBencanaBinding
import com.adit.poskologistikapp.models.Bencana

class BencanaActivityAdapter(private var bencana : List<Bencana>, private var listener : onClickBencanaAdapter) : RecyclerView.Adapter<BencanaActivityAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemBencanaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BencanaActivityAdapter.MyViewHolder {
        return MyViewHolder(ItemBencanaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BencanaActivityAdapter.MyViewHolder, position: Int) {
        holder.binding.tvNamaBencana.text = bencana[position].nama
        holder.binding.tvDetail.text = bencana[position].detail
        holder.binding.tvTanggal.text = bencana[position].tanggal
        holder.binding.ivFoto.load(bencana[position].foto)
        
        holder.binding.edit.setOnClickListener { 
            listener.edit(bencana[position])
        }
        
        holder.binding.hapus.setOnClickListener {
            listener.delete(bencana[position])
        }
        
    }

    override fun getItemCount() = bencana.size
}

interface onClickBencanaAdapter{
    fun edit(bencana : Bencana)
    fun delete(bencana : Bencana)
}