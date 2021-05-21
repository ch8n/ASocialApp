package com.ch8n.linkedin.di

import android.content.Context
import com.ch8n.linkedin.data.local.prefs.AppPrefs

// How Dependency Injection working?
// checkout my post explaining it in details : https://chetangupta.net/native-di/
object Provider {

    fun provideAppPrefs(applicationContext: Context): AppPrefs = AppPrefs(applicationContext)
    // fun provideUserRepo() = TODO()
    //fun provideViewModelFactory() = TODO()
}