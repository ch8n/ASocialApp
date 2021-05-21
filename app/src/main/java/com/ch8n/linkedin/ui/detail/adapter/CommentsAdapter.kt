package com.ch8n.linkedin.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch8n.linkedin.data.models.Comment
import com.ch8n.linkedin.databinding.ListItemCommentBinding
import com.ch8n.linkedin.utils.cancelPendingImage
import com.ch8n.linkedin.utils.loadImage

class CommentsAdapter private constructor(
    diffUtil: DiffUtil.ItemCallback<Comment>
) : ListAdapter<Comment, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentViewHolder(
            ListItemCommentBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentViewHolder -> holder.onBind(getItem(position))
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is CommentViewHolder -> holder.onClear()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Comment,
                newItem: Comment
            ): Boolean =
                oldItem == newItem
        }

        fun newInstance() = CommentsAdapter(DIFF_CALLBACK)
    }
}


class CommentViewHolder(
    private val binding: ListItemCommentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(comment: Comment) = with(binding) {
        imageAvatarComment.loadImage(comment.userAvatar)
        textCommentName.text = comment.userName
        textCommentContent.text = comment.message
    }

    fun onClear() {
        binding.imageAvatarComment.cancelPendingImage()
    }
}
