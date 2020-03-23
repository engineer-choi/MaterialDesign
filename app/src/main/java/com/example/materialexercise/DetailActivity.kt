package com.example.materialexercise

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.materialexercise.recycler.Poster
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val parmasExtraName = "parmasExtraName"
        const val posterExtraName = "posterExtraName"
        fun startActivity(
            context : Context,
            transformationLayout: TransformationLayout,
            poster : Poster
        ) {
            if(context is Activity){
                val bundle = transformationLayout.withView(transformationLayout, poster.name)
                val intent = Intent(context,DetailActivity::class.java)
                intent.putExtra(parmasExtraName, transformationLayout.getParcelableParams())
                intent.putExtra(posterExtraName, poster)
                context.startActivity(intent, bundle)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra(parmasExtraName))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }
    private fun init(){
        intent.getParcelableExtra<Poster>(posterExtraName)?.let{
            Glide.with(this)
                .load(it.poster)
                .into(img_detailView)
            tv_detail_title.text = it.name
            tv_detail_description.text = it.description
        }
    }
}
