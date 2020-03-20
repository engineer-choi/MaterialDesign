package com.example.materialexercise.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_poster_menu.view.*

class PosterMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(poster : Poster){
        itemView.run {
            Glide.with(context).load(poster.poster).into(img_poster_post)
            item_poster_title.text = poster.name
            setOnClickListener {

            }
        }
    }
}