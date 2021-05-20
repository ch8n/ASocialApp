package com.ch8n.linkedin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ch8n.linkedin.databinding.ActivityMainBinding
import com.ch8n.linkedin.ui.feeds.FeedsFragment
import com.ch8n.linkedin.ui.home.HomeFragment
import com.ch8n.linkedin.ui.login.LoginFragment
import com.ch8n.linkedin.ui.post.PostFragment
import com.ch8n.linkedin.ui.router.Router
import com.ch8n.linkedin.utils.base.ViewBindingActivity
import com.ch8n.linkedin.utils.commitTransaction

class MainActivity : ViewBindingActivity<ActivityMainBinding>(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup(): Unit = with(binding) { toHomeScreen() }

    override fun toLoginScreen() = commitTransaction(LoginFragment())

    override fun toHomeScreen() = commitTransaction(HomeFragment())

    override fun toFeedScreen() = commitTransaction(FeedsFragment())

    override fun toPostScreen() = commitTransaction(PostFragment())
}