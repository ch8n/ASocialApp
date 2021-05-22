package com.ch8n.linkedin.ui.feeds

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.linkedin.data.models.Feed
import com.ch8n.linkedin.data.repos.PostRepository
import com.ch8n.linkedin.utils.Result
import com.ch8n.linkedin.utils.asLiveData
import kotlinx.coroutines.launch

class FeedsViewModel(
    private val postRepository: PostRepository,
) : ViewModel() {

    init {
        getSocialFeeds()
    }

    private val _socialFeeds = MutableLiveData<List<Feed>>()
    val socialFeeds = _socialFeeds.asLiveData()

    private val _error = MutableLiveData<Pair<Exception, String>>()
    val error = _error.asLiveData()

    fun getSocialFeeds() {
        viewModelScope.launch {
            val result = Result.build { postRepository.getSocialFeeds() }
            when (result) {
                is Result.Error -> _error.value = result.error to "Something Went Wrong!"
                is Result.Success -> _socialFeeds.value = result.value
            }
        }
    }


}