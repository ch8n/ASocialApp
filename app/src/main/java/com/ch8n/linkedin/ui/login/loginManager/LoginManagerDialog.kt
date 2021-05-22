package com.ch8n.linkedin.ui.login.loginManager

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.databinding.LayoutLoginManagerBinding
import com.ch8n.linkedin.ui.login.di.LoginDI
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.base.ViewBindingBottomSheet
import com.ch8n.linkedin.utils.setVisible

class LoginManagerDialog : ViewBindingBottomSheet<LayoutLoginManagerBinding>() {

    companion object {
        const val TAG = "NoteDialog"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutLoginManagerBinding
        get() = LayoutLoginManagerBinding::inflate

    private val sharedViewModel by lazy { LoginDI.provideSharedViewModel(this) }

    override fun onDestroyView() {
        super.onDestroyView()
        accountAdapter = null
    }

    private var accountAdapter: AccountAdapter? = null

    override fun setup(): Unit = with(binding) {

        val accounts = User.mockUsers.map {
            Account(
                id = it.id,
                message = "",
                userId = it.id,
                userName = it.userName,
                userAvatar = it.avatarUrl
            )
        }

        AccountAdapter.newInstance(object : RecyclerInteraction<Account> {
            override fun onClick(payLoad: Account) {
                accountAdapter?.submitList(listOf(payLoad)) {
                    containerPassword.setVisible(true)
                    editPassword.requestFocus()
                    buttonLogin.setOnClickListener {
                        val userId = payLoad.userId
                        val password = editPassword.text.toString()
                        sharedViewModel.loginUser(userId, password)
                    }
                }
            }
        }).also { listAccount.adapter = it }
            .also { accountAdapter = it }
            .also {
                it.submitList(accounts)
                sharedViewModel.setLoading(false)
            }

    }
}

