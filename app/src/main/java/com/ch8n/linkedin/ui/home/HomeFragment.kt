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
            .also { pagerNotes.adapter = it }
            .also { pagerNotes.setPageTransformer(ZoomOutPageTransformer()) }
            .let {
                TabLayoutMediator(tabs, pagerNotes) { tab, position ->
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
                    isEnabled = pagerNotes.currentItem != 0
                    if (isEnabled) {
                        pagerNotes.currentItem = pagerNotes.currentItem - 1
                    } else {
                        requireActivity().onBackPressed()
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

}