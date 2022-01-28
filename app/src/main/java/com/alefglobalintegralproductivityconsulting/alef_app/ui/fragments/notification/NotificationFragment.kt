package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.notification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private lateinit var mNotificationViewModel: NotificationViewModel
    private lateinit var mBinding: FragmentNotificationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNotificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        mBinding = FragmentNotificationBinding.bind(view)
    }
}