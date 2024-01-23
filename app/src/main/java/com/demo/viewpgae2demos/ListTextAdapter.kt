package com.demo.viewpgae2demos

import android.widget.TextView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ListTextAdapter(layoutResId: Int, data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item:  String) {

        val tvTitle: TextView = holder.getView<TextView>(R.id.tv_title)
        val rvIt: RelativeLayout = holder.getView<RelativeLayout>(R.id.rv_it)
        var isOdd:Boolean= holder.layoutPosition % 2 != 0

        val colorWhite=ContextCompat.getColor( context, R.color.purple_200)
        val colorsGry=ContextCompat.getColor( context, R.color.green)
        rvIt.setBackgroundColor( if (isOdd)colorsGry else colorWhite)
        tvTitle.text=item
    }
}