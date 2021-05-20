package com.ch8n.linkedin.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.databinding.FragmentPostBinding
import com.ch8n.linkedin.utils.base.ViewBindingFragment


class PostFragment : ViewBindingFragment<FragmentPostBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPostBinding
        get() = FragmentPostBinding::inflate

    override fun setup() {

    }

}