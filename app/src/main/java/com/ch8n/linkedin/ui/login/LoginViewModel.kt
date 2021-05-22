package com.ch8n.linkedin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.linkedin.data.local.prefs.AppPrefs
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.repos.UserRepository
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import com.ch8n.linkedin.utils.Result
import com.ch8n.linkedin.utils.asLiveData
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val appPrefs: AppPrefs
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user = _user.asLiveData()

    private val _error = MutableLiveData<Pair<Exception, String>>()
    val error = _error.asLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading.asLiveData()

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    fun loginUser(userId: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            val result = Result.build { userRepository.loginUser(userId, password) }
            when (result) {
                is Result.Error -> _error.value = result.error to "Something Went Wrong!"
                is Result.Success -> {
                    appPrefs.isLogin = true
                    appPrefs.userId = result.value.id
                    _loading.value = false
                    _user.value = result.value
                }
            }
        }
    }


}