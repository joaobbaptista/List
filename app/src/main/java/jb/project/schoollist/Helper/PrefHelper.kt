package jb.project.schoollist.Helper

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

/**
 * Created by joao.neto on 18/09/2017.
 */
class PrefHelper (context: Context) {
    private val PREFS_FILENAME = "jb.project.schoollist.prefs"
    private val USER_ID = "user_id"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var userId: Int
        get() = prefs.getInt(USER_ID, -1)
        set(value) = prefs.edit().putInt(USER_ID, value).apply()
}