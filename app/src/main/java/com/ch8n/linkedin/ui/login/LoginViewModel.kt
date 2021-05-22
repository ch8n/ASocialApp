package com.ch8n.linkedin.ui.login

import Contact
import ContentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch8n.linkedin.data.local.prefs.AppPrefs
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.repos.UserRepository
import com.ch8n.linkedin.ui.login.loginManager.CONTACT_PICKER
import com.ch8n.linkedin.ui.login.loginManager.LOGIN_MANAGER
import com.ch8n.linkedin.ui.login.loginManager.SIGNIN
import com.ch8n.linkedin.utils.Result
import com.ch8n.linkedin.utils.asLiveData
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val appPrefs: AppPrefs,
    private val contentManager: ContentManager
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user = _user.asLiveData()

    private val _loggableUser = MutableLiveData<List<User>>()
    val recentUsers = _loggableUser.asLiveData()

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts = _contacts.asLiveData()

    private val _error = MutableLiveData<Pair<Exception, String>>()
    val error = _error.asLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading = _loading.asLiveData()

    fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    fun getRecentUser() {
        viewModelScope.launch {
            _loading.value = true
            val result = Result.build { userRepository.getLoggableUsers() }
            when (result) {
                is Result.Error -> _error.value = result.error to "Something Went Wrong!"
                is Result.Success -> {
                    _loading.value = false
                    _loggableUser.value = result.value
                }
            }
        }
    }

    fun getContacts() {
        viewModelScope.launch {
            _loading.value = true
            val result = Result.build { contentManager.getContacts() }
            when (result) {
                is Result.Error -> _error.value = result.error to "Something Went Wrong!"
                is Result.Success -> {
                    _loading.value = false
                    _contacts.value = result.value
                }
            }
        }
    }


    fun loginUser(authType: SIGNIN, userId: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            appPrefs.loginType = when (authType) {
                SIGNIN.LOGIN_MANAGER -> LOGIN_MANAGER
                SIGNIN.CONTACT_PICKER -> CONTACT_PICKER
            }
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