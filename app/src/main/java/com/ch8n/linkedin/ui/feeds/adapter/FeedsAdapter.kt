package com.ch8n.linkedin.ui.feeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch8n.linkedin.data.models.Feed
import com.ch8n.linkedin.databinding.ListItemFeedsBinding
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.cancelPendingImage
import com.ch8n.linkedin.utils.loadImage


class FeedsAdapter private constructor(
    diffUtil: DiffUtil.ItemCallback<Feed>,
    private val feedsInteraction: RecyclerInteraction<Feed>
) : ListAdapter<Feed, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FeedViewHolder(
            ListItemFeedsBinding.inflate(LayoutInflater.from(parent.context)),
            feedsInteraction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedViewHolder -> holder.onBind(getItem(position))
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is FeedViewHolder -> holder.onClear()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Feed>() {
            override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem.post.id == newItem.post.id

            override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean =
                oldItem == newItem
        }

        fun newInstance(feedsInteraction: RecyclerInteraction<Feed>) =
            FeedsAdapter(DIFF_CALLBACK, feedsInteraction)
    }
}


class FeedViewHolder(
    private val binding: ListItemFeedsBinding,
    private val feedsInteraction: RecyclerInteraction<Feed>
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(feed: Feed) = with(binding) {
        imageAvatar.loadImage(feed.user.avatarUrl)
        textCreatorName.text = feed.user.userName
        textPostContent.text = feed.post.content
        buttonComments.text = "${feed.post.comments.size}"
        root.setOnClickListener {
            feedsInteraction.onClick(feed)
        }
    }

    fun onClear() {
        binding.imageAvatar.cancelPendingImage()
    }
}
