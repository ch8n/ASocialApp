package com.ch8n.linkedin.di

import android.content.Context
import com.ch8n.linkedin.data.remote.apis.config.BaseUrl

/**
 * How Dependency Injection working?
 * checkout my post explaining it in details : https://chetangupta.net/native-di/
 */
object Injector {

    lateinit var appContext: Context

    fun init(appContext: Context) {
        Injector.appContext = appContext
    }

    private val okhttpClient by lazy { Provider.provideOkHttpClient() }
    private val retrofitClient by lazy {
        Provider.provideRetrofitClient(
            okhttpClient,
            BaseUrl.BASE_SERVER
        )
    }
    private val apiManager by lazy { Provider.provideApiManager(retrofitClient) }
    private val userSource by lazy { Provider.provideUserDataSource(apiManager) }
    private val postSource by lazy { Provider.providePostDataSource(apiManager) }

    val userRepository by lazy { Provider.provideUserRepo(userSource) }
    val postRepository by lazy { Provider.providePostRepo(postSource) }
    val appPrefs by lazy { Provider.provideAppPrefs(appContext) }
}