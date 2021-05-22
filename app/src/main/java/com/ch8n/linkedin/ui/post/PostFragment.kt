package com.ch8n.linkedin.ui.post

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.data.models.Feed
import com.ch8n.linkedin.databinding.FragmentPostBinding
import com.ch8n.linkedin.ui.feeds.adapter.FeedsAdapter
import com.ch8n.linkedin.ui.post.di.PostDI
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.ch8n.linkedin.utils.setVisible
import com.google.android.material.snackbar.Snackbar


class PostFragment : ViewBindingFragment<FragmentPostBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPostBinding
        get() = FragmentPostBinding::inflate

    private var postAdapter: FeedsAdapter? = null
    private val viewModel: UserPostViewModel by lazy { PostDI.provideFeedViewModel(this) }

    override fun setup(): Unit = with(binding) {

        pullRefresh.isRefreshing = true
        containerNoPost.textEmpty.text = "You didn't Post"

        viewModel.error.observe(viewLifecycleOwner) {
            it ?: return@observe
            pullRefresh.isRefreshing = false
            val (error, message) = it
            Log.e(TAG, message, error)
            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.userFeeds.observe(viewLifecycleOwner) {
            it ?: return@observe
            containerNoPost.root.setVisible(it.isEmpty())
            postAdapter?.submitList(it) {
                pullRefresh.isRefreshing = false
            }
        }

        pullRefresh.setOnRefreshListener {
            viewModel.getUserFeeds()
        }

        FeedsAdapter
            .newInstance(object : RecyclerInteraction<Feed> {
                override fun onClick(payLoad: Feed) {
                    router.toDetailScreen(payLoad)
                }
            })
            .also { postAdapter = it }
            .also { listUserPost.adapter = it }
    }

    override val TAG: String
        get() = "PostFragment"

}