package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.OnCloseBackPress
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var mBinding: FragmentSettingsBinding
    private var mOnCloseListener: OnCloseBackPress? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnCloseBackPress) mOnCloseListener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentSettingsBinding.bind(view)

        onBackPress()
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