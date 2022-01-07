package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding
    private var isClosedPreview = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init() {
        val args = intent.extras
        if (args != null) {
            isClosedPreview = args.getBoolean(AppConstants.IS_CLOSED_PREVIEW_TIME)
        }
        if (isClosedPreview) {
            delayTime(300)
        } else {
            delayTime(1000)
        }
    }

    private fun delayTime(delayMillis: Int) {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis.toLong())
    }
}