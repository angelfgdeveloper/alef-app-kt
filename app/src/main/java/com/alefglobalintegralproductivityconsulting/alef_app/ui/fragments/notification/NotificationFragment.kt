package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.notification

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.Result
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.OnCloseBackPress
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
    private var mOnCloseListener: OnCloseBackPress? = null

    private val mViewModel by viewModels<NotificationViewModel> {
        NotificationViewModelFactory(
            NotificationRepoImpl(RemoteNotificationDataSource())
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnCloseBackPress) mOnCloseListener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentNotificationBinding.bind(view)

        onBackPress()
        setupNotifications()
        setupMarkViewAllNotifications()
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

    private fun setupMarkViewAllNotifications() {
        mBinding.btnMarkAllView.setOnClickListener {
            selectedMark()
        }
    }

    private fun selectedMark() {
        mViewModel.setMarkViewAllNotifications(true).observe(viewLifecycleOwner) { result ->
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

                    for (isMak in result.data) {
                        mBinding.btnMarkAllView.isEnabled = isMak.isView
                    }

                    mAdapter.setMarkAllView(result.data)
                }
            }

        }
    }

    override fun onNotificationClick(notification: Notification) {
        Log.d("NotificationFragment", notification.toString())
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mOnCloseListener?.onCloseActivity(false)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
}