package com.ch8n.linkedin.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import com.ch8n.linkedin.data.models.Comment
import com.ch8n.linkedin.data.models.Post
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.databinding.FragmentDetailBinding
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.ch8n.linkedin.utils.clearLineLimits
import com.ch8n.linkedin.utils.loadImage
import com.ch8n.linkedin.utils.setVisible
import com.ch8n.linkedin.utils.toast


class DetailFragment : ViewBindingFragment<FragmentDetailBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    private val feedOrExit: Feed
        get() {
            val feed = (arguments?.getParcelable(DETAIL_FEED) as? Feed)
            feed ?: router.toFeedScreen().also { toast("Something went wrong!") }
            return feed ?: Feed.empty
        }

    override fun setup() = with(binding) {
        setupContent(feedOrExit.post, feedOrExit.user)
        setupComments(feedOrExit.post.comments)
        applyBackPressBehaviour()
    }

    private fun setupComments(comments: List<Comment>) {
        
    }

    private fun setupContent(post: Post, user: User) = with(binding.containerPost) {
        textCreatorName.text = user.userName
        buttonComments.setVisible(false)
        imageAvatar.loadImage(user.avatarUrl)
        textPostContent.text = post.content
        textPostContent.clearLineLimits()
    }

    private fun applyBackPressBehaviour() = with(binding) {
        requireActivity().onBackPressedDispatcher.addCallback(
            /* LifecycleOwner*/this@DetailFragment,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    router.toHomeScreen()
                }
            }
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        const val DETAIL_FEED = "feed"

        fun newInstance(feed: Feed) = DetailFragment().apply {
            arguments = bundleOf(DETAIL_FEED to feed)
        }
    }
}