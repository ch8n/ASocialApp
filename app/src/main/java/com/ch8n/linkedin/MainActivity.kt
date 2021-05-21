package com.ch8n.linkedin

import android.os.Bundle
import android.view.LayoutInflater
import com.ch8n.linkedin.databinding.ActivityMainBinding
import com.ch8n.linkedin.di.Injector
import com.ch8n.linkedin.ui.detail.DetailFragment
import com.ch8n.linkedin.ui.feeds.FeedsFragment
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import com.ch8n.linkedin.ui.home.HomeFragment
import com.ch8n.linkedin.ui.login.LoginFragment
import com.ch8n.linkedin.ui.post.PostFragment
import com.ch8n.linkedin.ui.router.Router
import com.ch8n.linkedin.utils.base.ViewBindingActivity
import com.ch8n.linkedin.utils.commitTransaction
import com.ch8n.linkedin.utils.requiredFragmentContainer

class MainActivity : ViewBindingActivity<ActivityMainBinding>(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.init(applicationContext)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup(): Unit = with(binding) {
        val isUserLoggedIn = Injector.appPrefs.isLogin
        if (isUserLoggedIn) {
            toHomeScreen()
        } else {
            toLoginScreen()
        }
    }

    override fun toLoginScreen() = commitTransaction(LoginFragment(), addToStack = false)

    override fun toHomeScreen() = commitTransaction(HomeFragment(), addToStack = false)

    override fun toFeedScreen() = commitTransaction(FeedsFragment())

    override fun toPostScreen() = commitTransaction(PostFragment())

    override fun toDetailScreen(feed: Feed) = commitTransaction(DetailFragment.newInstance(feed))

    override fun back() {
        supportFragmentManager.popBackStackImmediate()
    }
}