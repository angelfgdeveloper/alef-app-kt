package com.companyglobal.alef_app.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.companyglobal.alef_app.core.utils.Validators
import com.companyglobal.alef_app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        methods()
    }

    private fun methods() {
        setSupportActionBar(mBinding.toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.title = ""
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        mBinding.btnRegister.setOnClickListener { validUser() }

        mBinding.etConfPassword.setOnEditorActionListener { v: TextView?, actionId: Int, _: KeyEvent? ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validUser()
                handled = true
            }
            handled
        }
    }

    private fun validUser() {
        val email: String = mBinding.etEmail.text.toString().trim()
        val password: String = mBinding.etPassword.text.toString().trim()
        val confPassword: String = mBinding.etConfPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if (Validators.isValidEmail(email)) {
                if (Validators.isValidPassword(password)) {
                    if (password == confPassword) {
                        goToHome()
                    } else {
                        Toast.makeText(
                            this,
                            "Por favor verifique las contraseñas, son distintas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Requiere una contraseña mayor a 5 caracteres",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Correo invalido", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Se necesitan rellenar todos los campos", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun goToHome() {
//        val userToken =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkFuZ2VsIiwiaWF0IjoxNTE2MjM5MDIyfQ.5sqP7aP7XI_UOCQLvRQuWTDCusnyq-WVsBex8rrX_ic"
//        SharedPreferencesManager.setStringValue(AppConstants.USER_TOKEN, userToken)
//
//        val intent = Intent(this@RegisterActivity, NavigationActivity::class.java)
//        intent.putExtra(AppConstants.IS_LOGIN_USER, true)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(intent)
//        finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}