package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashScreenBinding

    private var isClosedPreview = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        init()
    }

    private fun init() {
        val args = intent.extras
        if (args != null) {
            isClosedPreview = args.getBoolean(AppConstants.IS_CLOSED_PREVIEW_TIME)
        }

        if (isClosedPreview) {
            delayTime(300L)
        } else {
            delayTime(1000L)
        }
    }

    private fun delayTime(delayMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis)
    }
}