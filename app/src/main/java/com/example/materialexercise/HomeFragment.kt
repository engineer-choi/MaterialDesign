package com.example.materialexercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.materialexercise.MockUtil.getMockPosters
import com.example.materialexercise.recycler.PosterAdapter
import com.example.materialexercise.recycler.PosterMenuAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = PosterAdapter()
            .apply {
            addPosterList(getMockPosters())
        }
        recyclerView_menu.adapter = PosterMenuAdapter()
            .apply {
            addPosterList(getMockPosters())
        }
        fab.setOnClickListener{
            if(!transformationLayout.isTransforming){
                backgroundView.visibility = View.VISIBLE
            }
            transformationLayout.startTransform()
        }
        menu_home.setOnClickListener{
            if(!transformationLayout.isTransforming){
                backgroundView.visibility = View.GONE
            }
            transformationLayout.finishTransform()
        }
    }
}
