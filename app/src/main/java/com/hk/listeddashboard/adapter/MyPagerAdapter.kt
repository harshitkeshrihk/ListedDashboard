package com.hk.listeddashboard.adapter

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hk.listeddashboard.UI.LinksFragment
import com.hk.listeddashboard.UI.LinksListFragment
import com.hk.listeddashboard.UI.RecentLinkListFragment


class MyPagerAdapter(fragment : Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->LinksListFragment()
            1-> RecentLinkListFragment()
            else -> LinksFragment()
        }

    }
}