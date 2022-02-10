package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}