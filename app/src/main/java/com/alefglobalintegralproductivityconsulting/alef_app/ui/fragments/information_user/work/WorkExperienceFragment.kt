package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.work

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentWorkExperienceBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel
import com.google.android.material.textfield.TextInputEditText

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
        addMonths()
    }

    private fun addMonths() {
        with(mBinding) {
            mInfoUserViewModel.getMonthList().observe(viewLifecycleOwner) { months ->
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, months)

                val autoCompleteTextViewMonthsList = arrayListOf(
                    atvStartMonth1, atvEndMonth1, atvStartMonth2, atvEndMonth2, atvStartMonth3,
                    atvEndMonth3
                )

                addAllMonths(autoCompleteTextViewMonthsList, adapter)
            }
        }
    }

    private fun addAllMonths(
        atvMonths: ArrayList<AutoCompleteTextView>, adapter: ArrayAdapter<String>
    ) {
        for (atvMonth in atvMonths) {
            with(atvMonth) {
                setAdapter(adapter)
                setOnItemClickListener { parent, _, position, id -> }
            }
        }
    }

    private fun setupTextFields() {
        with(mBinding) {

            rgWorkExperience.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbYesWorkExperience -> {
                        fabNext.visibility = View.VISIBLE
                        fabNext.isEnabled = false
                        llWorkExperiences.visibility = View.VISIBLE
                    }
                    R.id.rbNoWorkExperience -> {
                        fabNext.visibility = View.VISIBLE
                        fabNext.isEnabled = true
                        llWorkExperiences.visibility = View.GONE
                    }
                }
            }

            val editTextDescriptionList =
                arrayListOf(etDescription1, etDescription2, etDescription3)
            enableScrollEditText(editTextDescriptionList)

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

    private fun enableScrollEditText(descriptions: ArrayList<TextInputEditText>) {
        for (etDescription in descriptions) {
            with(etDescription) {
                setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        if (hasFocus()) {
                            v?.parent?.requestDisallowInterceptTouchEvent(true)
                            when (event?.action?.and(MotionEvent.ACTION_MASK)) {
                                MotionEvent.ACTION_SCROLL -> {
                                    v?.parent?.requestDisallowInterceptTouchEvent(false)
                                    return true
                                }
                            }
                        }
                        return false
                    }
                })
            }
        }
    }

    private fun onBackPress() {
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