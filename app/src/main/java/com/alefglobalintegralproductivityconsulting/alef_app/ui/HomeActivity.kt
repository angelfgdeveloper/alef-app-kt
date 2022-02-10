package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mBinding: ActivityHomeBinding

    private var isLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val args = intent.extras
        if (args != null) {
            isLogin = args.getBoolean(AppConstants.IS_LOGIN_USER)
        }

        if (!isLogin) {
            hideItem(false)
        } else {
            hideItem(true)
        }

        setSupportActionBar(mBinding.appBarHome.toolbar)

        val drawerLayout: DrawerLayout = mBinding.drawerLayout
        val navView: NavigationView = mBinding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_login,
                R.id.nav_postulation,
                R.id.nav_favorites,
                R.id.nav_notification,
                R.id.nav_curriculum,
                R.id.nav_work_with_us,
                R.id.nav_settings,
                R.id.nav_logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.itemTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
        navView.itemIconTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.black))

        val bundle = bundleOf(AppConstants.IS_LOGIN_USER to isLogin)
        Navigation.findNavController(this, R.id.nav_host_fragment_content_home)
            .navigate(R.id.nav_home, bundle)

    }

    private fun hideItem(isVisible: Boolean) {
        val menu: Menu = mBinding.navView.menu
        menu.findItem(R.id.nav_login).isVisible = !isVisible
        menu.findItem(R.id.nav_login).setOnMenuItemClickListener {
            finish()
            true
        }

        menu.findItem(R.id.nav_postulation).isVisible = isVisible
        menu.findItem(R.id.nav_favorites).isVisible = isVisible
        menu.findItem(R.id.nav_notification).isVisible = isVisible
        menu.findItem(R.id.nav_curriculum).isVisible = isVisible
        menu.findItem(R.id.nav_settings).isVisible = isVisible

        menu.findItem(R.id.configuration_section).isVisible = isVisible
        menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            finish()
            true
        }

        val hView = mBinding.navView.getHeaderView(0)
        if (!isVisible) {
            val ivWarning = hView.findViewById<ImageView>(R.id.ivWarning)
            val civAvatar = hView.findViewById<ImageView>(R.id.civAvatar)
            val tvFullName = hView.findViewById<TextView>(R.id.tvFullName)
            val tvSpecialist = hView.findViewById<TextView>(R.id.tvSpecialist)

            civAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_login))

            ivWarning.visibility = View.INVISIBLE
            tvFullName.text = "Ingresa a tu cuenta"
            tvSpecialist.text = "Para tener más detalles y una mejor experiencia"
        }

        mBinding.btnLogout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}