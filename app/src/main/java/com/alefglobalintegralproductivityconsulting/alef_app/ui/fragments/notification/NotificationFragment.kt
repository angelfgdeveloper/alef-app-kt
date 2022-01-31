package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.Result
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Notification
import com.alefglobalintegralproductivityconsulting.alef_app.data.remote.notification.RemoteNotificationDataSource
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentNotificationBinding
import com.alefglobalintegralproductivityconsulting.alef_app.domain.notification.NotificationRepoImpl
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.notification.NotificationViewModel
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.notification.NotificationViewModelFactory
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.notification.adapter.NotificationAdapter
import java.util.*

class NotificationFragment : Fragment(R.layout.fragment_notification),
    NotificationAdapter.OnNotificationClickListener {

    private lateinit var mBinding: FragmentNotificationBinding
    private lateinit var mAdapter: NotificationAdapter

    private val mViewModel by viewModels<NotificationViewModel> {
        NotificationViewModelFactory(
            NotificationRepoImpl(RemoteNotificationDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentNotificationBinding.bind(view)

        setupNotifications()
    }

    private fun setupNotifications() {
        mViewModel.fetchNotifications().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Failure -> {
                    mBinding.llLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Result.Loading -> {
                    mBinding.llLoading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    mBinding.llLoading.visibility = View.GONE

                    if (result.data.isEmpty()) {
                        mBinding.llDisconnected.visibility = View.VISIBLE
                        mBinding.llConnected.visibility = View.GONE
                        return@observe
                    } else {
                        mBinding.llDisconnected.visibility = View.GONE
                        mBinding.llConnected.visibility = View.VISIBLE
                    }

                    mAdapter = NotificationAdapter(result.data, this)
                    mBinding.rvNotifications.adapter = mAdapter
                }
            }

        }
    }

    override fun onNotificationClick(notification: Notification) {
        Log.d("NotificationFragment", notification.toString())
    }
}