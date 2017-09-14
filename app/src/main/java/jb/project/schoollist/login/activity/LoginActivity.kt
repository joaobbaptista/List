package jb.project.schoollist.login.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import jb.project.schoollist.Helper.Utils
import jb.project.schoollist.Helper.database
import jb.project.schoollist.R
import jb.project.schoollist.home.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.db.RowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.longToast


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        sign_in_btn.setOnClickListener {
            validateFields(email = email_edt.text.toString().trim(), password = password_edt.text.toString().trim())
        }

    }

    private fun validateFields(email: String, password: String) {
        if (!Utils().isValidEmail(email))
            email_edt.setError(getString(R.string.error_invalid_email))
        else if (password.isBlank())
            password_edt.setError(getString(R.string.error_field_required))
        else {

            val rowSelected = database.use {
                select("User", "user_id")
                        .whereArgs("(email = {email}) and (password = {password})",
                                "email" to email,
                                "password" to password)
            }

            val rowInserted = database.use {
                insert("User",
                        "email" to email,
                        "password" to password)
            }

            if (rowInserted.equals(-1)) {
                longToast(R.string.error_sql_save)
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
