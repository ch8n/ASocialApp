package com.ch8n.linkedin.data.remote.apis.config

import com.ch8n.linkedin.BuildConfig
import com.ch8n.linkedin.data.remote.apis.sources.post.FakePostService
import com.ch8n.linkedin.data.remote.apis.sources.post.PostService
import com.ch8n.linkedin.data.remote.apis.sources.users.FakeUserService
import com.ch8n.linkedin.data.remote.apis.sources.users.UserService
import retrofit2.Retrofit
import java.lang.IllegalArgumentException

class ApiManager constructor(private val retrofit: Retrofit) {

    private fun <T> createApi(clazz: Class<T>): T {
        return if (BuildConfig.DEBUG) fakeRetrofitResolver(clazz) else retrofit.create(clazz)
    }

    val userService: UserService by lazy { createApi(UserService::class.java) }
    val postService: PostService by lazy { createApi(PostService::class.java) }
}

@Suppress("UNCHECKED_CAST")
fun <T> fakeRetrofitResolver(clazz: Class<T>): T {
    return when (clazz) {
        PostService::class.java -> FakePostService as T
        UserService::class.java -> FakeUserService as T
        else -> throw IllegalStateException("Wrong Service added")
    }
}
