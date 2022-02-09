package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.work

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentWorkExperienceBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel

class WorkExperienceFragment : Fragment(R.layout.fragment_work_experience) {

    private lateinit var mBinding: FragmentWorkExperienceBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()
    private var listener: StepViewListener? = null

    private var mIsStepView = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentWorkExperienceBinding.bind(view)

        mInfoUserViewModel.getAcademicUser().observe(viewLifecycleOwner) { academicUser ->
            if (academicUser.identificationCard) {
                mIsStepView = true
            }
        }

        onBackPress()
        setupTextFields()
    }

    private fun setupTextFields() {
        with(mBinding) {

            fabReturn.setOnClickListener {
                if (mIsStepView) {
                    listener?.onSelectStepView(1, R.id.postgraduateFragment)
                } else {
                    listener?.onSelectStepView(1, R.id.academicFragment)
                }
            }
            fabNext.setOnClickListener {
                Toast.makeText(requireContext(), "En desarrollo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (mIsStepView) {
                        listener?.onSelectStepView(1, R.id.postgraduateFragment)
                    } else {
                        listener?.onSelectStepView(1, R.id.academicFragment)
                    }
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}