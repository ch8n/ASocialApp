package com.ch8n.linkedin.data.remote.apis.sources.post

import com.ch8n.linkedin.data.models.Post
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.remote.apis.config.ApiManager
import com.ch8n.linkedin.data.remote.apis.config.AppAPI
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET(AppAPI.Post.GET_SOCIAL_FEEDS)
    suspend fun getSocialFeeds(): List<Feed>

    @GET(AppAPI.Post.GET_USER_POST)
    suspend fun getUserPost(@Path("userId") userId: String): List<Feed>

}

interface PostSource {
    suspend fun getSocialFeeds(): List<Feed>
    suspend fun getUserPost(userId: String): List<Feed>
}

class PostSourceProvider constructor(private val apiManager: ApiManager) : PostSource {
    override suspend fun getSocialFeeds() = apiManager.postService.getSocialFeeds()
    override suspend fun getUserPost(userId: String) = apiManager.postService.getUserPost(userId)
}

object FakePostService : PostService {
    override suspend fun getSocialFeeds(): List<Feed> {
        // backend logic
        delay(500)
        val posts = Post.mockPosts
        val users = User.mockUsers
        val feeds = posts.map { post ->
            val creator = users.first { it.id == post.userId }
            Feed(post, creator)
        }
        return feeds
    }

    override suspend fun getUserPost(userId: String): List<Feed> {
        // backend logic
        delay(500)
        val posts = Post.mockPosts
        val currentUser = User.mockUsers.first { it.id == userId }
        val feeds = posts
            .filter { post -> post.userId == currentUser.id }
            .map { Feed(it, currentUser) }
        return feeds
    }
}
