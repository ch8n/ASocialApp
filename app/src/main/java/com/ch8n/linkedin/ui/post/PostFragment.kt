package com.ch8n.linkedin.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.data.models.Post
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.databinding.FragmentPostBinding
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import com.ch8n.linkedin.ui.feeds.adapter.FeedsAdapter
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.ch8n.linkedin.utils.setVisible


class PostFragment : ViewBindingFragment<FragmentPostBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPostBinding
        get() = FragmentPostBinding::inflate

    private var postAdapter: FeedsAdapter? = null

    override fun setup(): Unit = with(binding) {
        FeedsAdapter
            .newInstance(object : RecyclerInteraction<Feed> {
                override fun onClick(payLoad: Feed) {
                    router.toDetailScreen(payLoad)
                }
            })
            .also { postAdapter = it }
            .also { listUserPost.adapter = it }
            .also {
                val feeds = Post.fakePosts
                    .map { post ->
                        val creator = User.mockUsers.first { user ->
                            user.id == post.userId
                        }
                        Feed(post, creator)
                    }.filter {
                        it.post.userId == User.superUser.id
                    }
                containerNoPost.root.setVisible(feeds.isEmpty())
                containerNoPost.textEmpty.text = "You didn't Post"
                it.submitList(feeds)
            }
    }

}