package com.demo.viewpgae2demos

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.demo.viewpgae2demos.databinding.ActivityMainLayoutBinding
import com.demo.viewpgae2demos.ui.view.SlidingTabLayout3

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainLayoutBinding
    private lateinit var adapter: FragmentStateAdapter
    lateinit var title: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initData()
    }
      private fun initData() {
        title = ArrayList()

        for (i in 0 until 6){
            title.add("tab$i")
        }
        adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                var  fragment= TextFragment()
                var builder = Bundle()
                builder.putString("title", title[position])
                fragment.arguments = builder
                return   fragment
            }

            override fun getItemCount(): Int {
                return title.size
            }
        }
          binding.viewPager.init(adapter, userInputEnabled = true, saveEnabled = false, pageLimit = title.size)
          binding.tab.setViewPager(binding.viewPager, title)
    }

    override fun onResume() {
        super.onResume()
        binding.tab.initColor(this)
    }
    private fun ViewPager2.init(
        bindAdapter: FragmentStateAdapter,
        userInputEnabled: Boolean = false,
        saveEnabled: Boolean = false,
        pageLimit: Int
    ): ViewPager2 {
        adapter = bindAdapter
        isSaveEnabled = saveEnabled
        isUserInputEnabled = userInputEnabled
        offscreenPageLimit = pageLimit
        return this
    }

    private fun SlidingTabLayout3.initColor(
        context: Context
    ): SlidingTabLayout3 {
            textSelectColor = ContextCompat.getColor(context, R.color.text_color)
            textUnselectColor = ContextCompat.getColor(context, R.color.text_grey_color)
            underlineColor = ContextCompat.getColor(context, R.color.color_search_line)
        return this
    }




}



