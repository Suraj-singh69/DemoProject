package com.example.simplecameramobileapp.ui.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.simplecameramobileapp.ui.activity.MainActivity

class ViewPagerAdapter(fragment: MainActivity, var flist: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return flist.size
    }

    override fun createFragment(position: Int): Fragment {

        return flist.get(position)
    }

}
