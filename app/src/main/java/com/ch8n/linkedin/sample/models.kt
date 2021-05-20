package com.ch8n.linkedin.sample

import com.github.javafaker.Faker
import java.util.*

data class User(
    val id: String,
    val password: String,
    val userName: String,
    val avatarUrl: String,
    val postIds: MutableList<String>
) {
    companion object {
        val superUser = with(Faker.instance()) {
            User(
                id = "1234567890",
                password = "qwerty",
                avatarUrl = avatar().image(),
                userName = name().fullName(),
                postIds = mutableListOf()
            )
        }

        private fun fakeUser(id: String): User = with(Faker.instance()) {
            User(
                id = id,
                password = "qwerty",
                avatarUrl = avatar().image(),
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

data class Post(
    val id: String,
    val userId: String,
    val content: String,
    val comments: List<Comment>
) {
    companion object {
        val fakePost
            get() = with(Faker.instance()) {
                val post = Post(
                    id = UUID.randomUUID().toString(),
                    userId = User.mockUsers.random().id,
                    content = friends().quote(),
                    comments = mutableListOf<Comment>().apply {
                        repeat((0..5).random()) {
                            add(Comment.fakeComment)
                        }
                    }
                )
                val postCreator = User.mockUsers.first { it.id == post.userId }
                postCreator.postIds.add(post.id)
                post
            }

        val mockPost by lazy {
            mutableListOf<Post>().apply {
                repeat(20) { add(fakePost) }
            }
        }
    }
}

data class Comment(
    val id: String,
    val message: String,
    val userId: String
) {
    companion object {
        val fakeComment
            get() = with(Faker.instance()) {
                Comment(
                    id = UUID.randomUUID().toString(),
                    message = elderScrolls().quote(),
                    userId = User.mockUsers.random().id
                )
            }
    }
}