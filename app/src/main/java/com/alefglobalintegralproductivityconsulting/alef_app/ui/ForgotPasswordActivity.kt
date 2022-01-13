package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alefglobalintegralproductivityconsulting.alef_app.core.Validators
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
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

        mBinding.btnRecoverPassword.setOnClickListener { sendEmail() }
        mBinding.etRecoverPassword.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendEmail()
                handled = true
            }
            handled
        }
    }

    private fun sendEmail() {
        val email: String = mBinding.etRecoverPassword.text.toString().trim()
        if (Validators.isValidEmail(email)) {
            mBinding.btnRecoverPassword.isEnabled = false
            mBinding.etRecoverPassword.isEnabled = false
            Toast.makeText(
                this,
                "En unos momentos recibira un correo con instrucciones, revise su bandeja de entrada",
                Toast.LENGTH_LONG
            ).show()

            Handler(Looper.getMainLooper()).postDelayed({
                mBinding.btnRecoverPassword.isEnabled = true
                mBinding.etRecoverPassword.isEnabled = true
                mBinding.etRecoverPassword.setText("")
            }, 3500)

        } else {
            Toast.makeText(this, "Correo electrónico invalido ó vacio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}