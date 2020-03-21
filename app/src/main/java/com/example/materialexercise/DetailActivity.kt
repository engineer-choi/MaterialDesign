package com.example.materialexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }
    private fun init(){
        Glide.with(this).load("https://cdn.pixabay.com/photo/2018/03/07/17/15/balloon-3206530__340.jpg").into(img_detailView)
    }
}
