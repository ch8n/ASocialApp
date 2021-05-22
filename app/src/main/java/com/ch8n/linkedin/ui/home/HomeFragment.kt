package com.ch8n.linkedin.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.ch8n.linkedin.databinding.FragmentHomeBinding
import com.ch8n.linkedin.ui.home.adapter.HomePagerAdapter
import com.ch8n.linkedin.ui.home.adapter.ZoomOutPageTransformer
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private var homePagerAdapter: HomePagerAdapter? = null

    override fun setup() = with(binding) {
        HomePagerAdapter
            .newInstance(this@HomeFragment.requireActivity())
            .also { homePagerAdapter = it }
            .also { pagerHome.adapter = it }
            .also { pagerHome.setPageTransformer(ZoomOutPageTransformer()) }
            .also { pagerHome.isUserInputEnabled = false }
            .let {
                TabLayoutMediator(tabs, pagerHome) { tab, position ->
                    tab.text = it.getTabName(position)
                }
            }.attach()

        applyBackPressBehaviour()
    }

    private fun applyBackPressBehaviour() = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(
            /* LifecycleOwner*/this@HomeFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = pagerHome.currentItem != 0
                    if (isEnabled) {
                        pagerHome.currentItem = pagerHome.currentItem - 1
                    } else {
                        router.back()
                    }
                }
            }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        homePagerAdapter?.onClear()
        homePagerAdapter = null
    }

    override val TAG: String get() = "HomeFragment"

}