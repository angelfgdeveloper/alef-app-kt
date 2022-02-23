package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.core.OnVacantClickListener
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.OnCloseBackPress
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.SharedPreferencesManager
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityHomeBinding
import com.alefglobalintegralproductivityconsulting.alef_app.services.GoogleVerify
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), OnVacantClickListener, OnCloseBackPress {

    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mToolbar: Toolbar
    private lateinit var mNavController: NavController
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView

    private var mGoogleSignInClient: GoogleSignInClient? = null

    private var vacant: String? = null
    private var vacantInfoExtra: String? = null
    private var isLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mToolbar = mBinding.appBarHome.toolbar
        mDrawerLayout = mBinding.drawerLayout
        mNavigationView = mBinding.navView
        mNavController = findNavController(R.id.nav_host_fragment_content_home)
        setSupportActionBar(mToolbar)

        val args = intent.extras
        if (args != null) {
            isLogin = args.getBoolean(AppConstants.IS_LOGIN_USER)
            vacant = args.getString(AppConstants.JSON_VACANT)
            vacantInfoExtra = args.getString(AppConstants.JSON_DETAILS_VACANT)
        }

        if (!isLogin) {
            hideItem(false)
        } else {
            hideItem(true)
            mGoogleSignInClient = GoogleVerify.signInGoogle(this)
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_login, R.id.nav_postulation, R.id.nav_favorites,
                R.id.nav_notification, R.id.nav_curriculum, R.id.nav_work_with_us,
                R.id.nav_settings, R.id.nav_logout
            ), mDrawerLayout
        )

        setupActionBarWithNavController(mNavController, appBarConfiguration)
        mNavigationView.setupWithNavController(mNavController)

        mNavigationView.itemTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black))
        mNavigationView.itemIconTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, android.R.color.black))

        addDrawerMenu(mDrawerLayout)

        mNavController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == R.id.vacantDetailsFragment) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                mToolbar.visibility = View.GONE
            } else {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                mToolbar.visibility = View.VISIBLE
                mToolbar.title = nd.label
                mToolbar.setNavigationIcon(R.drawable.ic_drawer_menu)
            }
        }

        if (!vacant.isNullOrEmpty() && !vacantInfoExtra.isNullOrEmpty()) {
            onVacantDetails(vacant!!, vacantInfoExtra, true)
        } else {
            val bundle = bundleOf(AppConstants.IS_LOGIN_USER to isLogin)
            Navigation.findNavController(this, R.id.nav_host_fragment_content_home)
                .navigate(R.id.nav_home, bundle)
        }

    }

    private fun addDrawerMenu(drawerLayout: DrawerLayout) {
        val drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, mBinding.appBarHome.toolbar,
            R.string.drawer_open, R.string.drawer_close
        )

        drawerToggle.isDrawerIndicatorEnabled = false

        drawerToggle.setToolbarNavigationClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer_menu)
    }

    private fun hideItem(isVisible: Boolean) {
        val menu: Menu = mNavigationView.menu
        menu.findItem(R.id.nav_login).isVisible = !isVisible
        menu.findItem(R.id.nav_login).setOnMenuItemClickListener {
            gotToLogin()
            true
        }

        menu.findItem(R.id.nav_postulation).isVisible = isVisible
        menu.findItem(R.id.nav_favorites).isVisible = isVisible
        menu.findItem(R.id.nav_notification).isVisible = isVisible
        menu.findItem(R.id.nav_curriculum).isVisible = isVisible
        menu.findItem(R.id.nav_settings).isVisible = isVisible

        menu.findItem(R.id.configuration_section).isVisible = isVisible
        menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            if (mGoogleSignInClient == null) {
                finish()
            } else {
                signOut()
            }
            SharedPreferencesManager.removeAllData(AppConstants.USER_TOKEN)
            SharedPreferencesManager.removeAllData(AppConstants.USER_ID_GOOGLE)
            SharedPreferencesManager.removeAllData(AppConstants.USER_PICTURE_PROFILE)
            true
        }

        val hView = mNavigationView.getHeaderView(0)
        if (!isVisible) {
            val ivWarning = hView.findViewById<ImageView>(R.id.ivWarning)
            val civAvatar = hView.findViewById<ImageView>(R.id.civAvatar)
            val tvFullName = hView.findViewById<TextView>(R.id.tvFullName)
            val tvSpecialist = hView.findViewById<TextView>(R.id.tvSpecialist)

            civAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_login))

            ivWarning.visibility = View.INVISIBLE
            tvFullName.text = "Ingresa a tu cuenta"
            tvSpecialist.text = "Para tener más detalles y una mejor experiencia"
        } else {
            val civAvatar = hView.findViewById<ImageView>(R.id.civAvatar)

            val pictureGoogle =
                SharedPreferencesManager.getStringValue(AppConstants.USER_PICTURE_PROFILE)
            if (pictureGoogle != "") {
                Glide.with(this).load(pictureGoogle).centerCrop().into(civAvatar)
            }
        }

        mBinding.btnLogout.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun signOut() {
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(this) {
            val i = Intent(this, SplashScreenActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun gotToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onVacantDetails(
        jsonVacant: String, jsonVacantInfoExtra: String?, isActivity: Boolean,
        idFragment: Int?
    ) {
        val bundle = bundleOf(
            AppConstants.DETAILS_VACANT to jsonVacant,
            AppConstants.VACANT_INFO_EXTRA to jsonVacantInfoExtra,
            AppConstants.IS_ACTIVITY to isActivity,
            AppConstants.ID_NAV_FRAGMENT to idFragment
        )

        Navigation.findNavController(this, R.id.nav_host_fragment_content_home)
            .navigate(R.id.vacantDetailsFragment, bundle)
    }

    override fun onCloseActivity(isActivityClose: Boolean, idFragment: Int?) {
        if (isActivityClose) {
            finish()
        } else {
            val bundle = bundleOf(AppConstants.IS_LOGIN_USER to isLogin)
            Navigation.findNavController(this, R.id.nav_host_fragment_content_home)
                .navigate(idFragment!!, bundle)
        }
    }
}