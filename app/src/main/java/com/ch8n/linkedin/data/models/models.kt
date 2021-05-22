package com.ch8n.linkedin.data.models

import android.os.Parcelable
import com.github.javafaker.Faker
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Feed(val post: Post, val user: User) : Parcelable {
    companion object {
        val empty = Feed(Post.empty, User.empty)
    }
}

@Parcelize
data class User(
    val id: String,
    val password: String,
    val userName: String,
    val avatarUrl: String,
    val postIds: MutableList<String>
) : Parcelable {
    companion object {

        val empty = User(
            id = "",
            password = "",
            userName = "",
            avatarUrl = "",
            postIds = mutableListOf()
        )

        val superUser = with(Faker.instance()) {
            User(
                id = "1234567890",
                password = "qwerty",
                avatarUrl = "https://i.pravatar.cc/150?img=${(1..100).random()}",
                userName = "Chetan",
                postIds = mutableListOf()
            )
        }

        private fun fakeUser(id: String): User = with(Faker.instance()) {
            User(
                id = id,
                password = "qwerty",
                avatarUrl = "https://i.pravatar.cc/150?img=${(1..100).random()}",
                userName = hobbit().character(),
                postIds = mutableListOf()
            )
        }

        val mockUsers by lazy {
            listOf(
                superUser,
                fakeUser("1111"),
                fakeUser("2222"),
                fakeUser("3333"),
                fakeUser("4444")
            )
        }
    }
}

@Parcelize
data class Post(
    val id: String,
    val userId: String,
    val content: String,
    val comments: List<Comment>
) : Parcelable {
    companion object {
        val empty = Post(id = "", userId = "", content = "", comments = emptyList())

        private val fakePost
            get() = with(Faker.instance()) {
                val post = Post(
                    id = UUID.randomUUID().toString(),
                    userId = User.mockUsers.random().id,
                    content = friends().quote(),
                    comments = mutableListOf<Comment>().apply {
                        repeat((0..5).random()) {
                            add(Comment.mockComments.random())
                        }
                    }
                )
                val postCreator = User.mockUsers.first { it.id == post.userId }
                postCreator.postIds.add(post.id)
                post
            }

        val mockPosts by lazy {
            mutableListOf<Post>().apply {
                repeat(100) { add(fakePost) }
            }
        }
    }
}

@Parcelize
data class Comment(
    val id: String,
    val message: String,
    val userId: String,
    val userName: String,
    val userAvatar: String,
) : Parcelable {
    companion object {
        private val fakeComment
            get() = with(Faker.instance()) {
                val user = User.mockUsers.random()
                Comment(
                    id = UUID.randomUUID().toString(),
                    message = elderScrolls().quote(),
                    userId = user.id,
                    userName = user.userName,
                    userAvatar = user.avatarUrl,
                )
            }

        val mockComments: List<Comment> = mutableListOf<Comment>().apply {
            repeat(100) {
                add(fakeComment)
            }
        }
    }
}
