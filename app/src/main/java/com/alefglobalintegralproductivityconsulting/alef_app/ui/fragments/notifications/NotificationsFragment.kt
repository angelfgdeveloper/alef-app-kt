package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.notifications

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentHomeBinding
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentNotificationsBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home.HomeViewModel

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private lateinit var mNotificationsViewModel: NotificationsViewModel
    private lateinit var mBinding: FragmentNotificationsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentNotificationsBinding.bind(view)
        mNotificationsViewModel = ViewModelProvider(this)[NotificationsViewModel::class.java]
    }

}