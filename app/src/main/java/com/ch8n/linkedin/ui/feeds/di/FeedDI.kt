package com.ch8n.linkedin.ui.feeds.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch8n.linkedin.di.Injector
import com.ch8n.linkedin.ui.feeds.FeedsFragment
import com.ch8n.linkedin.ui.feeds.FeedsViewModel

@Suppress("UNCHECKED_CAST")
object FeedDI {

    private val feedViewModelFactory by lazy {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FeedsViewModel(Injector.postRepository) as T
            }
        }
    }

    fun provideFeedViewModel(fragment: FeedsFragment) =
        ViewModelProvider(fragment, feedViewModelFactory)
            .get(FeedsViewModel::class.java)
}