package com.adit.poskologistikapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemPoskoBinding
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.utilities.Constants
import com.google.android.gms.maps.model.LatLng

class PoskoActivityAdapter(private var posko : List<Posko>,private var context: Context ,private var listener: onClickPoskoAdapter) : RecyclerView.Adapter<PoskoActivityAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemPoskoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PoskoActivityAdapter.MyViewHolder {
        return MyViewHolder(ItemPoskoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PoskoActivityAdapter.MyViewHolder, position: Int) {
        holder.binding.tvNamaPosko.text = posko[position].nama
        holder.binding.tvJumlahPengungsi.text = posko[position].jumlah_pengungsi
        holder.binding.tvKontakHp.text = posko[position].kontak_hp
        holder.binding.tvLokasi.text = posko[position].lokasi

        val token = Constants.getToken(context)
        val level = Constants.getLevel(context)
        if(token == "UNDEFINED"){
            holder.binding.hapus.visibility = View.GONE
            holder.binding.edit.visibility = View.GONE
        }else{
            if(level != "Admin"){
                holder.binding.hapus.visibility = View.GONE
                holder.binding.edit.visibility = View.GONE
            }
        }


        holder.binding.hapus.setOnClickListener {
            listener.delete(posko[position])
        }

        holder.binding.edit.setOnClickListener {
            listener.edit(posko[position])
        }

        holder.itemView.setOnClickListener {
            listener.detail(posko[position])
        }

        holder.binding.kebutuhan.setOnClickListener{
            listener.kebutuhan(posko[position])
        }

        holder.binding.lokasi.setOnClickListener {
            val pcc = LatLng(posko[position].latitude?.toDouble()!!, posko[position].longitude?.toDouble()!!)
            val map = "http://maps.google.com/maps?q=loc:" + posko[position].latitude
                .toString() + "," + posko[position].longitude.toString() + " (" + posko[position].nama
                .toString() + ")"
            val gmmIntentUri: Uri = Uri.parse(map) as Uri
            System.err.println(pcc);
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        }
    }

    override fun getItemCount() = posko.size
}

interface onClickPoskoAdapter {
    fun edit(posko : Posko)
    fun delete(posko : Posko)
    fun detail(posko : Posko)
    fun kebutuhan(posko : Posko)
}
