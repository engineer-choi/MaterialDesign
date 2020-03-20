package com.example.materialexercise.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialexercise.R

class PosterMenuAdapter : RecyclerView.Adapter<PosterMenuViewHolder>(){
    private val items = mutableListOf<Poster>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterMenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterMenuViewHolder(
            inflater.inflate(
                R.layout.item_poster_menu,
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
    override fun onBindViewHolder(holder: PosterMenuViewHolder, position: Int) {
        holder.bind(items[position])
    }
}