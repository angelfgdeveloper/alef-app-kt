package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.Validators
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding
    private var toolbar: Toolbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)
        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        methods()
    }

    private fun methods() {
        toolbar = mBinding.toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle("")
        }

        mBinding.llLoading.visibility = View.VISIBLE
        val colorPurpleDark = Color.parseColor("#FF3700B3")
        mBinding.progressBar.indeterminateTintList = ColorStateList.valueOf(colorPurpleDark)

        if (Validators.isConnected(requireActivity())) {
            isConnected(mBinding.root)
        } else {
            mBinding.llLoading.visibility = View.GONE
            mBinding.llDisconnected.visibility = View.VISIBLE
        }
    }

    private fun isConnected(view: View) {

    }
}