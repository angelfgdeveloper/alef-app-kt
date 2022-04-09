package com.companyglobal.alef_app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.core.utils.MessageFactory
import com.companyglobal.alef_app.core.utils.SharedPreferencesManager
import com.companyglobal.alef_app.core.utils.Validators
import com.companyglobal.alef_app.data.model.auth.RequestRegister
import com.companyglobal.alef_app.data.remote.auth.AuthDataSource
import com.companyglobal.alef_app.databinding.ActivityRegisterBinding
import com.companyglobal.alef_app.domain.auth.AuthRepoImpl
import com.companyglobal.alef_app.presentation.auth.AuthViewModel
import com.companyglobal.alef_app.presentation.auth.AuthViewModelFactory
import com.companyglobal.alef_app.services.auth.RetrofitClientAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding

    private val mViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource(RetrofitClientAuth.webServiceAuth)
            )
        )
    }

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
                        authRegisterUser(email, password, confPassword)
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

    private fun authRegisterUser(email: String, password: String, confPassword: String) {
        val requestRegister = RequestRegister(email, password, "", confPassword)
//        MessageFactory.getDialog(this, R.layout.dialog_loading, MessageFactory.TYPE_LOADING)?.show()

        mViewModel.register(requestRegister).observe(this) { result ->
            when(result) {
                is Result.Failure -> {
                    Log.d("Auth-Register", result.toString())
                }
                is Result.Loading -> {
                    Log.d("Auth-Register", result.toString())
                }
                is Result.Success -> {
                    val accessToken = result.data.body()?.accessToken.toString()
                    val uid = result.data.body()?.userStore?.uid.toString()

                    if (accessToken.isNotEmpty() && accessToken != "null") {
                        SharedPreferencesManager.setStringValue(
                            AppConstants.USER_TOKEN,
                            accessToken
                        )

                        if (uid.isNotEmpty() && uid != "null") {
                            SharedPreferencesManager.setStringValue(
                                AppConstants.USER_ID_GOOGLE,
                                uid
                            )

                            goToHome(accessToken)
                        }
                    }
                }
            }
        }
    }

    private fun goToHome(accessToken: String = "") {
        SharedPreferencesManager.setStringValue(AppConstants.USER_TOKEN, accessToken)

        val intent = Intent(this@RegisterActivity, AvatarActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}