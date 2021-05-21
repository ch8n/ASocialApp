package com.ch8n.linkedin.data.remote.apis.sources.users

import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.remote.apis.config.ApiManager
import com.ch8n.linkedin.data.remote.apis.config.AppAPI
import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST(AppAPI.Auth.POST_LOGIN)
    suspend fun loginUser(userId: String, password: String): User

    @GET(AppAPI.User.GET_USER)
    suspend fun getUser(@Path("userId") userId: String): User

}

interface UserSource {
    suspend fun loginUser(userId: String, password: String): User
    suspend fun getUser(userId: String): User
}

class UserSourceProvider constructor(private val apiManager: ApiManager) : UserSource {
    override suspend fun loginUser(userId: String, password: String): User {
        return apiManager.userService.loginUser(userId, password)
    }

    override suspend fun getUser(userId: String): User {
        return apiManager.userService.getUser(userId)
    }
}

object FakeUserService : UserService {

    override suspend fun loginUser(userId: String, password: String): User {
        delay(1000)
        val users = User.mockUsers
        val currentUser = users.first { it.id == userId }
        if (currentUser.password != password) {
            throw Exception("Wrong Password")
        }
        return currentUser
    }

    override suspend fun getUser(userId: String): User {
        delay(1000)
        return User.mockUsers.first { it.id == userId }
    }
}
