package com.example.materialexercise.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.materialexercise.DetailActivity
import kotlinx.android.synthetic.main.item_poster.view.*
import kotlinx.android.synthetic.main.item_poster_menu.view.*
import kotlinx.android.synthetic.main.item_poster_menu.view.item_poster_title

class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(poster : Poster){
        itemView.run {
            Glide.with(context).load(poster.poster).into(item_poster_post)
            item_poster_title.text = poster.name
            item_poster_running_time.text = poster.playtime
            setOnClickListener {
                DetailActivity.startActivity(context,item_poster_transformationLayout,poster)
            }
        }
    }
}