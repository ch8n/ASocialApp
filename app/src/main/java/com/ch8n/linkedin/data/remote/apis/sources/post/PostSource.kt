package com.ch8n.linkedin.data.remote.apis.sources.post

import com.ch8n.linkedin.data.models.Comment
import com.ch8n.linkedin.data.models.User
import com.ch8n.linkedin.data.remote.apis.config.ApiManager
import com.ch8n.linkedin.data.remote.apis.config.AppAPI
import com.ch8n.linkedin.ui.feeds.adapter.Feed
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET(AppAPI.Post.GET_SOCIAL_FEEDS)
    suspend fun getSocialFeeds(): List<Feed>

    @GET(AppAPI.Post.GET_USER_POST)
    suspend fun getUserPost(): List<Feed>

    @GET(AppAPI.Post.GET_POST_COMMENTS)
    suspend fun getPostComments(@Path("postId") postId: String): List<Comment>

}

interface PostSource {
    suspend fun getSocialFeeds(): List<Feed>
    suspend fun getUserPost(): List<Feed>
    suspend fun getPostComments(postId: String): List<Comment>
}

class PostSourceProvider constructor(private val apiManager: ApiManager) : PostSource {
    override suspend fun getSocialFeeds() = apiManager.postService.getSocialFeeds()
    override suspend fun getUserPost() = apiManager.postService.getUserPost()
    override suspend fun getPostComments(postId: String) =
        apiManager.postService.getPostComments(postId)
}

//TODO create mock
//class FakePostSourceProvider constructor(private val apiManager: ApiManager) : PostSource {
//    override suspend fun getSocialFeeds() {
//
//    }
//
//    override suspend fun getUserPost() {
//
//    }
//
//    override suspend fun getPostComments(postId: String) {
//
//
//    }
//}
