package com.ch8n.linkedin.di

import android.content.Context

// How Dependency Injection working?
// checkout my post explaining it in details : https://chetangupta.net/native-di/
object Injector {

    lateinit var appContext: Context

    fun init(appContext: Context) {
        Injector.appContext = appContext
    }

    val prefs by lazy { Provider.provideAppPrefs(appContext) }
    // val repo by lazy { Provider.provideRepo() }


}