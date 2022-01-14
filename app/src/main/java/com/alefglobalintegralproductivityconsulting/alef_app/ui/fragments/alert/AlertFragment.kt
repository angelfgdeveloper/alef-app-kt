package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.alert

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentAlertBinding

class AlertFragment : Fragment(R.layout.fragment_alert) {

    private lateinit var mAlertViewModel: AlertViewModel
    private lateinit var mBinding: FragmentAlertBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAlertViewModel = ViewModelProvider(this)[AlertViewModel::class.java]
        mBinding = FragmentAlertBinding.bind(view)
    }
}