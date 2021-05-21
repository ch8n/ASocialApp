package com.ch8n.linkedin.ui.feeds

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.data.models.Post
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.databinding.FragmentFeedsBinding
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import com.ch8n.linkedin.ui.feeds.adapter.FeedsAdapter
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.ch8n.linkedin.utils.toast

class FeedsFragment : ViewBindingFragment<FragmentFeedsBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedsBinding
        get() = FragmentFeedsBinding::inflate

    private var feedsAdapter: FeedsAdapter? = null

    override fun setup(): Unit = with(binding) {
        FeedsAdapter
            .newInstance(object : RecyclerInteraction<Feed> {
                override fun onClick(payLoad: Feed) {
                    toast(payLoad.toString())
                    router.toDetailScreen(payLoad)
                }
            }
            ).also { feedsAdapter = it }
            .also { listFeed.adapter = it }
            .also {
                val feeds = Post.fakePosts.map { post ->
                    val creator = User.mockUsers.first { user ->
                        user.id == post.userId
                    }
                    Feed(post, creator)
                }
                it.submitList(feeds)
            }
    }

}