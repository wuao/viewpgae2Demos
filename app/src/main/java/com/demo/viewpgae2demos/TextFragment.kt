package com.demo.viewpgae2demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.demo.viewpgae2demos.databinding.FragmentTextLayoutBinding
import com.flyco.tablayout.SlidingTabLayout2

class TextFragment: Fragment() {


    private lateinit var adapter: FragmentStateAdapter
    private lateinit var title: ArrayList<String>
   private lateinit var   binding: FragmentTextLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextLayoutBinding.inflate(layoutInflater)
        initData()
        return binding.root
    }

    private fun initData() {
        title = ArrayList()
        val name=requireArguments().getString("title")
        for (i in 0 until 5){
            title.add(name+"SecondTab-$i")
        }
        adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                val  fragment= TextListFragment()
                val builder = Bundle()
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
        binding.tab.initColor(requireActivity())
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

    private fun SlidingTabLayout2.initColor(
        context: Context
    ): SlidingTabLayout2 {
        textSelectColor = ContextCompat.getColor(context, R.color.text_color)
        textUnselectColor = ContextCompat.getColor(context, R.color.text_grey_color)
        underlineColor = ContextCompat.getColor(context, R.color.color_search_line)
        return this
    }


}