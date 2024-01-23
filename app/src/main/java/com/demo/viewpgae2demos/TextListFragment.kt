package com.demo.viewpgae2demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.viewpgae2demos.databinding.FragmentTextListLayoutBinding
import com.demo.viewpgae2demos.ui.view.WrapContentLinearLayoutManager

class TextListFragment: Fragment() {
    private lateinit var adapter: ListTextAdapter
    private lateinit var title: ArrayList<String>
    private lateinit var binding: FragmentTextListLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextListLayoutBinding.inflate(layoutInflater)
        initData()
        return binding.root

    }

    private fun initData() {
        title=ArrayList()
        adapter=ListTextAdapter(R.layout.list_text_item_view, title)
        val name=requireArguments().getString("title")
        for (i in 0 until 50){
            title.add(name+"___ListItem-$i")
        }
       val layoutManager = WrapContentLinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        adapter.setList(title)
    }

}