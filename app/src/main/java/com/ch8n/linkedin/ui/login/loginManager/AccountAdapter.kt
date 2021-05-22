package com.ch8n.linkedin.ui.login.loginManager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch8n.linkedin.data.models.Comment
import com.ch8n.linkedin.databinding.ListItemCommentBinding
import com.ch8n.linkedin.utils.RecyclerInteraction
import com.ch8n.linkedin.utils.cancelPendingImage
import com.ch8n.linkedin.utils.loadImage

typealias Account = Comment

class AccountAdapter private constructor(
    private val listInteraction: RecyclerInteraction<Account>,
    diffUtil: DiffUtil.ItemCallback<Account>
) : ListAdapter<Account, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AccountViewHolder(
            ListItemCommentBinding.inflate(LayoutInflater.from(parent.context)),
            listInteraction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AccountViewHolder -> holder.onBind(getItem(position))
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is AccountViewHolder -> holder.onClear()
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Account>() {
            override fun areItemsTheSame(
                oldItem: Account,
                newItem: Account
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Account,
                newItem: Account
            ): Boolean =
                oldItem == newItem
        }

        fun newInstance(listInteraction: RecyclerInteraction<Account>) =
            AccountAdapter(listInteraction, DIFF_CALLBACK)
    }
}


class AccountViewHolder(
    private val binding: ListItemCommentBinding,
    private val listInteraction: RecyclerInteraction<Account>,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(account: Account) = with(binding) {
        imageAvatarComment.loadImage(account.userAvatar)
        textCommentName.text = account.userName
        textCommentContent.text = account.userId
        root.setOnClickListener {
            listInteraction.onClick(account)
        }
        
    }

    fun onClear() {
        binding.imageAvatarComment.cancelPendingImage()
    }
}
