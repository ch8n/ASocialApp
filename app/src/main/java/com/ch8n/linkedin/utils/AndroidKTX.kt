package com.ch8n.linkedin.utils

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ch8n.linkedin.R
import com.ch8n.linkedin.utils.base.ViewBindingActivity
import com.ch8n.linkedin.utils.base.ViewBindingFragment
import com.squareup.picasso.Picasso


interface RecyclerInteraction<T> {
    fun onClick(payLoad: T)
}

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this

fun AppCompatTextView.clearLineLimits() {
    maxLines = Integer.MAX_VALUE
    ellipsize = null
}

fun AppCompatImageView.loadImage(url: String) {
    Picasso.get()
        .let {
            if (url.isEmpty()) {
                it.load(R.drawable.ic_avatar)
            } else {
                it.load(url)
            }
        }
        .placeholder(R.drawable.ic_avatar)
        .resize(50, 50)
        .centerCrop()
        .into(this)
}

fun AppCompatImageView.cancelPendingImage() {
    Picasso.get().cancelRequest(this)
}

fun ViewBindingActivity<*>.toast(message: String) {
    Log.e("ch8n", message)
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ViewBindingFragment<*>.toast(message: String) {
    Log.e("ch8n", message)
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

fun ViewBindingActivity<*>.commitTransaction(
    fragment: ViewBindingFragment<*>,
    addToStack: Boolean = true,
    onCompleted: () -> Unit = {}
) {
    supportFragmentManager
        .also {
            if (isFinishing && !it.isStateSaved) {
                return
            }
        }
        .beginTransaction()
        .replace(requiredFragmentContainer().id, fragment)
        .apply {
            if (addToStack) addToBackStack(fragment.TAG)
        }
        .commit()
    supportFragmentManager.executePendingTransactions()
    Handler().post(onCompleted)
}

fun ViewBindingActivity<*>.requiredFragmentContainer(): FrameLayout {
    return findViewById(R.id.fragment_container)
        ?: throw IllegalStateException(
            "View needs FrameLayout with id fragment_container in order to do transaction"
        )
}

sealed class Result<out V, out E> {

    data class Success<out V>(val value: V) : Result<V, Nothing>()
    data class Error<out E>(val error: E) : Result<Nothing, E>()

    companion object {
        inline fun <V> build(function: () -> V): Result<V, Exception> =
            try {
                Success(function.invoke())
            } catch (e: Exception) {
                Error(e)
            }
    }
}