package jb.project.schoollist.Helper

import android.text.TextUtils

/**
 * Created by joao.neto on 13/09/2017.
 */
class Utils {
      fun isValidEmail(email: String): Boolean {
        return !email.isBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}