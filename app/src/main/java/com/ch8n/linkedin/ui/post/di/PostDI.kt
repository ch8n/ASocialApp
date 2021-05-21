package com.ch8n.linkedin.ui.post.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch8n.linkedin.di.Injector
import com.ch8n.linkedin.ui.post.PostFragment
import com.ch8n.linkedin.ui.post.UserPostViewModel


@Suppress("UNCHECKED_CAST")
object PostDI {

    private val UserPostViewModelFactory by lazy {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UserPostViewModel(Injector.postRepository, Injector.appPrefs) as T
            }
        }
    }

    fun provideFeedViewModel(fragment: PostFragment) =
        ViewModelProvider(fragment, UserPostViewModelFactory)
            .get(UserPostViewModel::class.java)
}