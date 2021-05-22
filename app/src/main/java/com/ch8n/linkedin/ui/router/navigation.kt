package com.ch8n.linkedin.ui.router

import com.ch8n.linkedin.data.models.Feed


interface Router {
    fun toLoginScreen()
    fun toHomeScreen()
    fun toFeedScreen()
    fun toPostScreen()
    fun toDetailScreen(feed: Feed)
    fun back()
}