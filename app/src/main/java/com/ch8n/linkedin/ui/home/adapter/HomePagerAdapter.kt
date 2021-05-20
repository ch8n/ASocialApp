package com.ch8n.linkedin.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ch8n.linkedin.ui.feeds.FeedsFragment
import com.ch8n.linkedin.ui.post.PostFragment


class HomePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments by lazy {
        mutableMapOf<String, Fragment>(
            "Feeds" to FeedsFragment(),
            "Post" to PostFragment()
        )
    }

    fun getTabName(position: Int): String {
        return fragments.keys.toList().get(position)
    }

    fun getFragment(position: Int): Fragment {
        return fragments.values.toList().get(position)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = getFragment(position)

    fun onClear() {
        fragments.clear()
    }
}