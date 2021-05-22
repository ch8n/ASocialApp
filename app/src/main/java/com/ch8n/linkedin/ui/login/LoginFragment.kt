package com.ch8n.linkedin.ui.login

import android.Manifest
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.databinding.FragmentLoginBinding
import com.ch8n.linkedin.ui.login.di.LoginDI
import com.ch8n.linkedin.ui.login.loginManager.LoginManagerDialog
import com.ch8n.linkedin.ui.login.loginManager.SIGNIN
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.google.android.material.snackbar.Snackbar
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>(),
    EasyPermissions.PermissionCallbacks {

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
            val loginManager =
                childFragmentManager.findFragmentByTag(LoginManagerDialog.TAG) as? LoginManagerDialog
            loginManager?.dismiss()
            val (error, message) = it
            Log.e(TAG, message, error)
            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.user.observe(viewLifecycleOwner) {
            it ?: return@observe
            viewModel.setLoading(false)
            val loginManager =
                childFragmentManager.findFragmentByTag(LoginManagerDialog.TAG) as? LoginManagerDialog
            loginManager?.dismiss()
            router.toHomeScreen()
        }

        buttonPasswordManager.setOnClickListener {
            viewModel.setLoading(true)
            val loginManager =
                childFragmentManager.findFragmentByTag(LoginManagerDialog.TAG) as? LoginManagerDialog
            loginManager?.dismiss()
            LoginManagerDialog().setLoginType(SIGNIN.LOGIN_MANAGER)
                .show(childFragmentManager, LoginManagerDialog.TAG)
        }

        buttonPhonePicker.setOnClickListener {
            checkContactsPermission()
        }
    }

    private fun hasContactsPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.READ_CONTACTS)
    }

    fun checkContactsPermission() {
        if (!hasContactsPermission()) {
            EasyPermissions.requestPermissions(
                this,
                "Contact picker wont work without permission...",
                1001,
                Manifest.permission.READ_CONTACTS
            )
        } else {
            onContactPick()
        }
    }

    fun onContactPick() {
        viewModel.setLoading(true)
        val loginManager =
            childFragmentManager.findFragmentByTag(LoginManagerDialog.TAG) as? LoginManagerDialog
        loginManager?.dismiss()
        LoginManagerDialog().setLoginType(SIGNIN.CONTACT_PICKER)
            .show(childFragmentManager, LoginManagerDialog.TAG)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 1001) {
            onContactPick()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override val TAG: String
        get() = "LoginFragment"
}