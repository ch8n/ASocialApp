package com.ch8n.linkedin.ui.login.loginManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.databinding.LayoutLoginManagerBinding
import com.ch8n.linkedin.ui.login.di.LoginDI
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.base.ViewBindingBottomSheet
import com.ch8n.linkedin.utils.setVisible
import java.util.*

enum class SIGNIN {
    LOGIN_MANAGER,
    CONTACT_PICKER
}

const val LOGIN_MANAGER = 0
const val CONTACT_PICKER = 1

class LoginManagerDialog : ViewBindingBottomSheet<LayoutLoginManagerBinding>() {

    companion object {
        const val TAG = "NoteDialog"
    }

    private val _dialogType = MutableLiveData<SIGNIN>()
    fun setLoginType(type: SIGNIN): LoginManagerDialog {
        _dialogType.value = type
        return this
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

        sharedViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            contacts ?: return@observe
            val accounts = contacts.map {
                Account(
                    id = it.number,
                    message = "",
                    userId = it.number,
                    userName = it.name,
                    userAvatar = ""
                )
            }
            accountAdapter?.submitList(accounts) {
                sharedViewModel.setLoading(false)
            }
        }

        sharedViewModel.recentUsers.observe(viewLifecycleOwner) { users ->
            users ?: return@observe
            val accounts = users.map {
                Account(
                    id = it.id,
                    message = "",
                    userId = it.id,
                    userName = it.userName,
                    userAvatar = it.avatarUrl
                )
            }
            accountAdapter?.submitList(accounts) {
                sharedViewModel.setLoading(false)
            }
        }

        when (_dialogType.value) {
            SIGNIN.LOGIN_MANAGER -> sharedViewModel.getRecentUser()
            SIGNIN.CONTACT_PICKER -> sharedViewModel.getContacts()
            else -> throw IllegalStateException("Wrong type login..")
        }

        AccountAdapter.newInstance(object : RecyclerInteraction<Account> {
            override fun onClick(payLoad: Account) {
                accountAdapter?.submitList(listOf(payLoad)) {
                    containerPassword.setVisible(true)
                    editPassword.requestFocus()
                    buttonLogin.setOnClickListener {
                        val userId = payLoad.userId
                        val password = editPassword.text.toString()
                        sharedViewModel.loginUser(
                            requireNotNull(_dialogType.value),
                            userId,
                            password
                        )
                    }
                }
            }
        }).also { listAccount.adapter = it }
            .also { accountAdapter = it }

    }
}

