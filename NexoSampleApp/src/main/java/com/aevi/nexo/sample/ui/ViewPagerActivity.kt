package com.aevi.nexo.sample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.aevi.nexo.sample.R
import com.aevi.nexo.sample.data.ScenarioController
import com.aevi.nexo.sample.data.ScenarioController.model
import com.aevi.nexo.sample.ui.page.InitiationFragment
import com.aevi.nexo.sample.ui.page.OutputFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerActivity : FragmentActivity() {

    private lateinit var mPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        mPager = findViewById(R.id.pager)
        mPager.adapter = ScreenSlidePagerAdapter(this)
        val tabs: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, mPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Request"
                1 -> "Response"
                else -> "Unknown"
            }
        }.attach()

        model.outputData.observe(this) { list ->
            tabs.getTabAt(1)?.let { tab ->
                if (list.isEmpty()) {
                    tab.removeBadge()
                } else {
                    tab.orCreateBadge
                }
            }
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.removeBadge()
            }
        })

        ScenarioController.setup(this)
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
            1 -> OutputFragment()
            else -> InitiationFragment()
        }
    }

    companion object {
        private const val NUM_PAGES = 2
    }
}
