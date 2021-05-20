package com.ch8n.linkedin.utils

import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.ch8n.linkedin.R
import com.ch8n.linkedin.utils.base.ViewBindingActivity

fun ViewBindingActivity<*>.commitTransaction(fragment: Fragment, onCompleted: () -> Unit = {}) {
    supportFragmentManager
        .also {
            if (isFinishing && !it.isStateSaved) {
                return
            }
        }
        .beginTransaction()
        .replace(requiredFragmentContainer().id, fragment)
        .runOnCommit { onCompleted.invoke() }
        .commit()
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