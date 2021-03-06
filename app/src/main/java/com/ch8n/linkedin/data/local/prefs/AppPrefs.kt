package com.ch8n.linkedin.data.local.prefs

import android.content.Context
import com.ch8n.linkedin.ui.login.loginManager.SIGNIN

const val App_Prefs_Name = "social_prefs"
const val KEY_LOGIN = "$App_Prefs_Name.is_login"
const val KEY_USER_ID = "$App_Prefs_Name.user_id"
const val KEY_LOGIN_TYPE = "$App_Prefs_Name.login_type"

class AppPrefs(appContext: Context) : PrefsConfig(appContext, App_Prefs_Name) {

    var isLogin: Boolean
        get() = prefs.get(KEY_LOGIN, false)
        set(value) = prefs.put(KEY_LOGIN, value)

    var userId: String
        get() = prefs.get(KEY_USER_ID, "")
        set(value) = prefs.put(KEY_USER_ID, value)

    var loginType: Int
        set(value) = prefs.put(KEY_LOGIN_TYPE, value)
        get() = prefs.get(KEY_LOGIN_TYPE, 0)

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
