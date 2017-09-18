package jb.project.schoollist.login.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import jb.project.schoollist.Helper.PrefHelper
import jb.project.schoollist.Helper.Utils
import jb.project.schoollist.Helper.database
import jb.project.schoollist.Helper.start
import jb.project.schoollist.R
import jb.project.schoollist.home.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.db.IntParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.longToast


class LoginActivity : AppCompatActivity() {

    var prefs: PrefHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        prefs = PrefHelper(this)

        sign_in_btn.setOnClickListener {
            validateFields(email = email_edt.text.toString().trim(), password = password_edt.text.toString().trim())
        }
    }

    private fun validateFields(email: String, password: String) {
        //VALIDATE FIELDS
        if (!Utils().isValidEmail(email))
            email_edt.setError(getString(R.string.error_invalid_email), null)
        else if (password.isBlank())
            password_edt.setError(getString(R.string.error_invalid_email), null)
        //NO ERROR FOUND
        else {
            val rowSelected = getUserId(email, password)
            if (rowSelected.equals(-1)) {
                Snackbar.make(container, "User not found, want to create a new one?", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok", { createUser(email, password) })
                        .show()
            } else {
                start<HomeActivity>()
            }
        }
    }

    private fun createUser(email: String, password: String) {
        val rowInserted = database.use {
            insert("User",
                    "email" to email,
                    "password" to password)
        }
        if (rowInserted.equals(-1)) {
            longToast(R.string.error_sql_save)
        } else {
            getUserId(email, password)
            start<HomeActivity>()
        }
    }

    private fun getUserId(email: String, password: String): Int {
        val rowSelected = database.use {
            select("User", "_id")
                    .whereArgs("(email = {email}) and (password = {password})",
                            "email" to email,
                            "password" to password).parseList(IntParser)
        }
        if (!rowSelected[0].equals(-1)) prefs!!.userId = rowSelected[0]
        return rowSelected[0]
    }
}
