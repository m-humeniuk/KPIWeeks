package com.dongumen.nickolas.kpiweeks.global.viewPager

import androidx.viewpager.widget.ViewPager

class OnPageSelected(private val pageSelected: (Int) -> Unit) : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) = pageSelected(position)
}