package com.ch8n.linkedin.ui.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.linkedin.data.local.prefs.AppPrefs
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.repos.PostRepository
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import com.ch8n.linkedin.utils.Result
import com.ch8n.linkedin.utils.asLiveData
import kotlinx.coroutines.launch

class UserPostViewModel(
    private val postRepository: PostRepository,
    private val appPrefs: AppPrefs
) : ViewModel() {

    init {
        getUserFeeds()
    }

    private val _userFeeds = MutableLiveData<List<Feed>>()
    val userFeeds = _userFeeds.asLiveData()

    private val _error = MutableLiveData<Pair<Exception, String>>()
    val error = _error.asLiveData()

    fun getUserFeeds() {
        viewModelScope.launch {
            //val userId = appPrefs.userId
            val userId = User.superUser.id
            val result = Result.build { postRepository.getUserPosts(userId) }
            when (result) {
                is Result.Error -> _error.value = result.error to "Something Went Wrong!"
                is Result.Success -> _userFeeds.value = result.value
            }
        }
    }


}