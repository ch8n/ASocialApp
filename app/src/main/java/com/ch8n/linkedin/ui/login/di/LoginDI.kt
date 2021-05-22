package com.ch8n.linkedin.ui.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch8n.linkedin.di.Injector
import com.ch8n.linkedin.ui.login.LoginFragment
import com.ch8n.linkedin.ui.login.LoginViewModel
import com.ch8n.linkedin.ui.login.loginManager.LoginManagerDialog
import com.ch8n.linkedin.ui.post.UserPostViewModel

@Suppress("UNCHECKED_CAST")
object LoginDI {

    private val LoginViewModelFactory by lazy {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(Injector.userRepository, Injector.appPrefs) as T
            }
        }
    }

    fun provideSharedViewModel(fragment: LoginManagerDialog) =
        ViewModelProvider(fragment.requireParentFragment(), LoginViewModelFactory)
            .get(LoginViewModel::class.java)

    fun provideLoginViewModel(fragment: LoginFragment) =
        ViewModelProvider(fragment, LoginViewModelFactory)
            .get(LoginViewModel::class.java)
}