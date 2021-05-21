package com.ch8n.linkedin.data.remote.apis.config

object BaseUrl {

    /**
     * Best to keep under gradle variables
     */
    private const val PROTOCOL_HTTP = "https://"
    private const val BASE_URL = "dev.ch8n.com"

    var BASE_SERVER = "$PROTOCOL_HTTP$BASE_URL"
        private set
}

object AppAPI {

    object User {
        const val GET_USER = "/user/{userId}"
    }

    object Post {
        const val GET_SOCIAL_FEEDS = "/feeds"
        const val GET_USER_POST = "/post/{userId}"
        const val GET_POST_COMMENTS = "/post/comments/{postId}"
    }

    object Auth {
        const val POST_LOGIN = "/login"
    }

}


const val NETWORK_TIMEOUT = 20L
