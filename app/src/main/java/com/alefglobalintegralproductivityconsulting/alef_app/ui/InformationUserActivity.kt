package com.alefglobalintegralproductivityconsulting.alef_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityInformationUserBinding
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityRegisterBinding

class InformationUserActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityInformationUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityInformationUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}