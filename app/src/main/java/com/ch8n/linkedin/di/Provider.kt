package com.ch8n.linkedin.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch8n.linkedin.data.local.prefs.AppPrefs
import com.ch8n.linkedin.data.remote.apis.config.ApiManager
import com.ch8n.linkedin.data.remote.apis.config.BaseUrl
import com.ch8n.linkedin.data.remote.apis.config.NETWORK_TIMEOUT
import com.ch8n.linkedin.data.remote.apis.sources.post.PostSource
import com.ch8n.linkedin.data.remote.apis.sources.post.PostSourceProvider
import com.ch8n.linkedin.data.remote.apis.sources.users.UserSource
import com.ch8n.linkedin.data.remote.apis.sources.users.UserSourceProvider
import com.ch8n.linkedin.data.repos.PostRepository
import com.ch8n.linkedin.data.repos.UserRepository
import com.ch8n.linkedin.ui.feeds.FeedsViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * How Dependency Injection working?
 * checkout my post explaining it in details : https://chetangupta.net/native-di/
 */
object Provider {

    fun provideAppPrefs(applicationContext: Context): AppPrefs = AppPrefs(applicationContext)

    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .build()

    fun provideRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun provideApiManager(retrofit: Retrofit) = ApiManager(retrofit)

    fun providePostDataSource(apiManager: ApiManager): PostSource = PostSourceProvider(apiManager)

    fun provideUserDataSource(apiManager: ApiManager): UserSource = UserSourceProvider(apiManager)

    fun providePostRepo(postSource: PostSource) = PostRepository(postSource)

    fun provideUserRepo(userSource: UserSource) = UserRepository(userSource)

}