package com.ch8n.linkedin.ui.login

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.databinding.FragmentLoginBinding
import com.ch8n.linkedin.ui.login.di.LoginDI
import com.ch8n.linkedin.ui.login.loginManager.LoginManagerDialog
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    private val viewModel by lazy { LoginDI.provideLoginViewModel(this) }

    override fun setup() = with(binding) {

        viewModel.loading.observe(viewLifecycleOwner) {
            it ?: return@observe
            loader.isRefreshing = it
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it ?: return@observe
            viewModel.setLoading(false)
            val loginManager = childFragmentManager.findFragmentByTag(LoginManagerDialog.TAG) as? LoginManagerDialog
            loginManager?.dismiss()
            val (error, message) = it
            Log.e(TAG, message, error)
            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.user.observe(viewLifecycleOwner) {
            it ?: return@observe
            viewModel.setLoading(false)
            val loginManager = childFragmentManager.findFragmentByTag(LoginManagerDialog.TAG) as? LoginManagerDialog
            loginManager?.dismiss()
            router.toHomeScreen()
        }


        // 1. add bottom sheet for login manager
        buttonPasswordManager.setOnClickListener {
            viewModel.setLoading(true)
            val loginManager = childFragmentManager
                .findFragmentByTag(LoginManagerDialog.TAG) as? LoginManagerDialog
            loginManager?.dismiss()
            LoginManagerDialog().show(childFragmentManager, LoginManagerDialog.TAG)
        }


        // 2. phone picker and password taker
        // do api call to validate user
        // update prefs and login user
        // write espresso test
    }

    override val TAG: String
        get() = "LoginFragment"

}