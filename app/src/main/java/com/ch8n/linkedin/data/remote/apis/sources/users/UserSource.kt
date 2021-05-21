package com.ch8n.linkedin.data.remote.apis.sources.users

import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.remote.apis.config.AppAPI
import retrofit2.http.GET
import retrofit2.http.Path


interface UserService {

    //@GET(TODO())
    suspend fun getUser(@Path("userId") userId: String): User

}