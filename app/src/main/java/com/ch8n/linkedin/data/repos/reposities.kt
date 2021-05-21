package com.ch8n.linkedin.data.repos

import com.ch8n.linkedin.data.remote.apis.sources.post.PostSource
import com.ch8n.linkedin.data.remote.apis.sources.users.UserSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(private val postSource: PostSource) {
    suspend fun getSocialFeeds() = withContext(Dispatchers.IO) {
        postSource.getSocialFeeds()
    }
}

class UserRepository(private val userSource: UserSource) {
    suspend fun loginUser(userId: String, password: String) = withContext(Dispatchers.IO) {
        userSource.loginUser(userId, password)
    }
}