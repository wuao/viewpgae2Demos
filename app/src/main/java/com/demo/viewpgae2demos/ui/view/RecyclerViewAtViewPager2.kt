package com.demo.viewpgae2demos.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class RecyclerViewAtViewPager2 : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var startX = 0
    private var startY = 0
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = abs(endX - startX)
                val disY = abs(endY - startY)
                getViewPager2(parent)?.isUserInputEnabled = !(disY > disX)
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                startX = 0
                startY = 0
                getViewPager2(parent)?.isUserInputEnabled = true
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun getViewPager2(viewParent: ViewParent?): ViewPager2? {

        return when {
            viewParent is ViewPager2 -> {
                viewParent
            }

            viewParent?.parent != null -> {
                getViewPager2(viewParent.parent)
            }

            else -> {
                null
            }
        }
    }
}