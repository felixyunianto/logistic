package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.adit.poskologistikapp.adapters.BencanaSliderAdapter.*
import com.adit.poskologistikapp.databinding.ItemBencaneSliderBinding
import com.adit.poskologistikapp.models.Bencana
import com.smarteist.autoimageslider.SliderViewAdapter
import coil.api.load

class BencanaSliderAdapter(private var bencana : List<Bencana>, private var listener : onClickBencanaSliderAdapter) : SliderViewAdapter<MyViewHolder>() {
    inner class MyViewHolder(val binding : ItemBencaneSliderBinding) : SliderViewAdapter.ViewHolder(binding.root)

    override fun getCount() = bencana.size

    override fun onCreateViewHolder(parent: ViewGroup): MyViewHolder {
        return MyViewHolder(ItemBencaneSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvAutoImage.text = bencana[position].nama
        holder.binding.ivImage.load(bencana[position].foto)
        holder.itemView.setOnClickListener {
            listener.detail(bencana[position])
        }
    }

}

interface onClickBencanaSliderAdapter{
    fun detail(bencana : Bencana)
}
