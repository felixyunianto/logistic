package com.adit.poskologistikapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.adit.poskologistikapp.databinding.ItemPetugasBinding
import com.adit.poskologistikapp.models.Petugas

class PetugasActivityAdapter(private var petugas : ArrayList<Petugas>, private var listener : onClickPetugasAdapter) : RecyclerView.Adapter<PetugasActivityAdapter.MyViewHolder>(),
    Filterable {
    private var petugasList = petugas
    private var petugasFull = ArrayList<Petugas>(petugasList)

    inner class MyViewHolder(val binding : ItemPetugasBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemPetugasBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvUsername.text = petugas[position].username
        holder.binding.tvPosko.text = petugas[position].nama_posko
        holder.binding.ubah.setOnClickListener {
            listener.edit(petugas[position])
        }
        holder.binding.hapus.setOnClickListener {
            listener.delete(petugas[position])
        }

        holder.binding.detail.setOnClickListener {
            listener.detail(petugas[position])
        }

    }

    override fun getItemCount() = petugas.size
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList  = ArrayList<Petugas>()

                if(constraint == null || constraint.length == 0){
                    filteredList.addAll(petugasFull)
                }else{
                    var filterPattern : String = constraint.toString().lowercase().trim()

                    for(item in petugasFull){
                        if(item.username?.lowercase()!!.contains(filterPattern)){
                            filteredList.add(item)
                        }
                    }
                }
                var results : FilterResults = FilterResults()
                results.values = filteredList

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                petugasList.clear()
                petugasList.addAll(results?.values as ArrayList<Petugas>)
                notifyDataSetChanged()
            }

        }
    }
}

interface onClickPetugasAdapter {
    fun edit(petugas : Petugas)
    fun delete(petugas : Petugas)
    fun detail(petugas: Petugas)
}
