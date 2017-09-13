package jb.project.schoollist.login.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import jb.project.schoollist.Helper.Utils
import jb.project.schoollist.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        sign_in_btn.setOnClickListener {
            validateFields(email = email_edt.text.toString(), password = password_edt.text.toString())
        }

    }

    private fun validateFields(email: String?, password: String?) {
        if (!Utils().isValidEmail(email!!))
            email_edt.setError(getString(R.string.error_invalid_email))
        else if (!TextUtils.isEmpty(password))
            password_edt.setError(getString(R.string.error_field_required))
    }

}
