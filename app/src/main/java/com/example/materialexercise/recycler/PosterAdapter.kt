package com.example.materialexercise.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialexercise.R

class PosterAdapter : RecyclerView.Adapter<PosterViewHolder>(){
    private val items = mutableListOf<Poster>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(
            inflater.inflate(
                R.layout.item_poster,
                parent,
                false
            )
        )
    }
    fun addPosterList(list : List<Poster>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        holder.bind(items[position])
    }
}