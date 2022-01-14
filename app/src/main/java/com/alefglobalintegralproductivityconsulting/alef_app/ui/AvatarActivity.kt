package com.alefglobalintegralproductivityconsulting.alef_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityAvatarBinding
import com.google.android.material.snackbar.Snackbar

class AvatarActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAvatarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAvatarBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnSelectAvatar.setOnClickListener {
            Toast.makeText(this, "En desarrollo", Toast.LENGTH_SHORT).show()
        }
    }
}