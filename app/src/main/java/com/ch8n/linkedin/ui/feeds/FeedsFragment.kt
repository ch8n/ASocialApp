package com.ch8n.linkedin.ui.feeds

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.linkedin.databinding.FragmentFeedsBinding
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import com.ch8n.linkedin.ui.feeds.adapter.FeedsAdapter
import com.ch8n.linkedin.ui.feeds.di.FeedDI
import com.ch8n.linkedin.ui.post.di.PostDI
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.ch8n.linkedin.utils.setVisible
import com.google.android.material.snackbar.Snackbar

class FeedsFragment : ViewBindingFragment<FragmentFeedsBinding>() {


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFeedsBinding
        get() = FragmentFeedsBinding::inflate

    private var feedsAdapter: FeedsAdapter? = null
    private val viewModel by lazy { FeedDI.provideFeedViewModel(this) }

    override fun setup(): Unit = with(binding) {

        pullRefresh.isRefreshing = true
        containerNoPost.textEmpty.text = "No Social Updates..."

        viewModel.error.observe(viewLifecycleOwner) {
            it ?: return@observe
            pullRefresh.isRefreshing = false
            val (error, message) = it
            Log.e(TAG, message, error)
            Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.socialFeeds.observe(viewLifecycleOwner) {
            it ?: return@observe
            containerNoPost.root.setVisible(it.isEmpty())
            feedsAdapter?.submitList(it) {
                pullRefresh.isRefreshing = false
            }
        }

        pullRefresh.setOnRefreshListener {
            viewModel.getSocialFeeds()
        }

        FeedsAdapter
            .newInstance(object : RecyclerInteraction<Feed> {
                override fun onClick(payLoad: Feed) {
                    router.toDetailScreen(payLoad)
                }
            }
            ).also { feedsAdapter = it }
            .also { listFeed.adapter = it }

    }

    override val TAG: String get() = "FeedsFragment"
}