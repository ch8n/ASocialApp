package com.ch8n.linkedin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.ch8n.linkedin.R
import com.ch8n.linkedin.databinding.FragmentHomeBinding
import com.ch8n.linkedin.ui.home.adapter.HomePagerAdapter
import com.ch8n.linkedin.ui.home.adapter.ZoomOutPageTransformer
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private var notePagerAdapter: HomePagerAdapter? = null

    override fun setup() = with(binding) {
        pagerNotes.adapter = HomePagerAdapter(this@HomeFragment.requireActivity())
            .also { notePagerAdapter = it }
        pagerNotes.setPageTransformer(ZoomOutPageTransformer())
        TabLayoutMediator(tabs, pagerNotes) { tab, position ->
            tab.text = notePagerAdapter?.getTabName(position)
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
        notePagerAdapter = null
    }

}