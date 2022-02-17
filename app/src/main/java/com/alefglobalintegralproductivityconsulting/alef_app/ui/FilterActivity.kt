package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupActionBar()

        with(mBinding) {
            btnApply.setOnClickListener {
                finish()
            }

            btnClean.setOnClickListener {
                Toast.makeText(applicationContext, "Limpieza de filtrado", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setupActionBar() {
        setSupportActionBar(mBinding.toolbar)
        mBinding.toolbar.setNavigationOnClickListener { finish() }
    }
}