package com.example.materialexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init(){
        with(main_viewpager){
            adapter = MainPagerAdapter(supportFragmentManager)
            offscreenPageLimit = 3
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) = Unit
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) = Unit

                override fun onPageSelected(position: Int) {
                    main_bottom_navigation.menu.getItem(position).isChecked = true
                }

            })
        }
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_one -> main_viewpager.currentItem = 0
                R.id.action_two -> main_viewpager.currentItem = 1
                R.id.action_three -> main_viewpager.currentItem = 2
            }
            true
        }
    }
}
