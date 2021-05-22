package com.ch8n.linkedin.data.remote.apis.sources.users

import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.remote.apis.config.ApiManager
import com.ch8n.linkedin.data.remote.apis.config.AppAPI
import com.ch8n.linkedin.di.Injector
import com.ch8n.linkedin.ui.login.loginManager.LOGIN_MANAGER
import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST(AppAPI.Auth.POST_LOGIN)
    suspend fun loginUser(userId: String, password: String): User

    @GET(AppAPI.User.GET_USER)
    suspend fun getLoggableUser(): List<User>

}

interface UserSource {
    suspend fun loginUser(userId: String, password: String): User
    suspend fun getLoggableUser(): List<User>
}

class UserSourceProvider constructor(private val apiManager: ApiManager) : UserSource {
    override suspend fun loginUser(userId: String, password: String): User {
        return apiManager.userService.loginUser(userId, password)
    }

    override suspend fun getLoggableUser(): List<User> {
        return apiManager.userService.getLoggableUser()
    }
}

object FakeUserService : UserService {

    override suspend fun loginUser(userId: String, password: String): User {
        delay(500)
        val currentUser = when (Injector.appPrefs.loginType) {
            LOGIN_MANAGER -> {
                val users = User.mockUsers
                users.first { it.id == userId }
            }
            else -> {
                User(userId, "qwerty", userId, "", mutableListOf())
            }
        }
        if (currentUser.password != password) {
            throw Exception("Wrong Password")
        }
        return currentUser
    }

    override suspend fun getLoggableUser(): List<User> {
        delay(500)
        return User.mockUsers
    }
}
