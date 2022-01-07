package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNavigationBinding

    private var isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val args = intent.extras
        if (args != null) {
            isLogin = args.getBoolean(AppConstants.IS_LOGIN_USER)
        }

        Toast.makeText(this, "Acceso por Login: $isLogin", Toast.LENGTH_SHORT).show()
        mBinding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (!isLogin) {
            mBinding.navView.menu.removeItem(R.id.navigation_profile)
            mBinding.navView.menu.removeItem(R.id.navigation_notifications)
        } else {
            mBinding.navView.menu.removeItem(R.id.navigation_exit)
        }

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation)
        NavigationUI.setupWithNavController(mBinding.navView, navController)

        val bundle = Bundle()
        bundle.putBoolean(AppConstants.IS_LOGIN_USER, isLogin)
        navController.navigate(R.id.navigation_home, bundle)
    }
}