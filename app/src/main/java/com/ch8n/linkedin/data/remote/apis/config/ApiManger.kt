package com.ch8n.linkedin.data.remote.apis.config

import com.ch8n.linkedin.data.remote.apis.sources.post.PostService
import com.ch8n.linkedin.data.remote.apis.sources.users.UserService
import retrofit2.Retrofit

class ApiManager constructor(private val retrofit: Retrofit) {

    private fun <T> createApi(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    val userService: UserService by lazy { createApi(UserService::class.java) }
    val postService: PostService by lazy { createApi(PostService::class.java) }

}


