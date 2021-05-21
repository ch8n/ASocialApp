package com.ch8n.linkedin.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.databinding.FragmentLoginBinding
import com.ch8n.linkedin.utils.base.ViewBindingFragment


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setup() {
        // TODO
        // 1. add bottom sheet for login manager
        // 2. phone picker and password taker
        // do api call to validate user
        // update prefs and login user
        // write espresso test
    }

    override val TAG: String
        get() = "LoginFragment"

}