package com.companyglobal.alef_app.ui.fragments.workus

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.utils.OnCloseBackPress
import com.companyglobal.alef_app.databinding.FragmentWorkUsBinding

class WorkUsFragment : Fragment(R.layout.fragment_work_us) {

    private lateinit var mBinding: FragmentWorkUsBinding
    private var mOnCloseListener: OnCloseBackPress? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnCloseBackPress) mOnCloseListener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentWorkUsBinding.bind(view)

        onBackPress()

        mBinding.btnWorkWithUs.setOnClickListener {
            Toast.makeText(requireContext(), "En desarrollo! :D", Toast.LENGTH_SHORT).show()
        }
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