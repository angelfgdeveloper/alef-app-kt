package com.companyglobal.alef_app.ui.fragments.curriculum

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.utils.OnCloseBackPress
import com.companyglobal.alef_app.databinding.FragmentCurriculumBinding

class CurriculumFragment : Fragment(R.layout.fragment_curriculum) {

    private lateinit var mBinding: FragmentCurriculumBinding
    private var mOnCloseListener: OnCloseBackPress? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnCloseBackPress) mOnCloseListener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentCurriculumBinding.bind(view)

        onBackPress()

        mBinding.chipIsVisible.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mBinding.chipIsVisible.text = "Perfil: Visible"
            } else {
                mBinding.chipIsVisible.text = "Perfil: Invisible"
            }
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