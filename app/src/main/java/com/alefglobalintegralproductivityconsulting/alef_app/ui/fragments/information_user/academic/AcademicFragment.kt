package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.academic

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.core.Validators.Companion.validateFields
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentAcademicBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.AcademicUser
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel
import kotlinx.android.synthetic.main.fragment_academic.*

class AcademicFragment : Fragment(R.layout.fragment_academic) {

    private lateinit var mBinding: FragmentAcademicBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()
    private var listener: StepViewListener? = null

    private var mAcademicLevel = ""
    private var mAcademicAdvance = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentAcademicBinding.bind(view)

        onBackPress()
        addSelectData()
        setupTextFields()

        mInfoUserViewModel.getAcademicUser().observe(viewLifecycleOwner, { academicUser ->
            with(mBinding) {
                atvAcademicLevel.setText(academicUser?.levelAcademic)
                if (atvAcademicLevel.text.toString().isNotEmpty()) {
                    llAcademic.visibility = View.VISIBLE

                    etSchool.setText(academicUser?.school)
                    atvAcademicAdvance.setText(academicUser?.academicAdvance)

                    setAcademicAdvance()

                    if (atvAcademicAdvance.text.toString().isNotEmpty()) {
                        llPeriod.visibility = View.VISIBLE
                    } else {
                        llPeriod.visibility = View.GONE
                    }

                } else {
                    llAcademic.visibility = View.GONE
                    llPeriod.visibility = View.GONE
                    etSchool.setText("")
                    atvAcademicAdvance.setText("")
                }

            }
        })

//        if (mAcademicLevel.isNotEmpty()) {
//            val fields = arrayListOf(
//                tilSchool, tilAcademicAdvance
//            )
//
//            Log.d("mAcademicLevel", fields.toString())
//        }
    }

    private fun addSelectData() {
        mInfoUserViewModel.getAcademicLevelList().observe(viewLifecycleOwner, { academicLevel ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, academicLevel)

            with(mBinding) {
                atvAcademicLevel.setAdapter(adapter)
                atvAcademicLevel.setOnItemClickListener { parent, _, position, id ->
                    mAcademicLevel = parent.getItemAtPosition(position).toString()
                    llAcademic.visibility = View.VISIBLE
                    llPeriod.visibility = View.GONE

                    etSchool.setText("")
                    atvAcademicAdvance.setText("")
                    setAcademicAdvance()
                }
            }
        })

    }

    private fun setAcademicAdvance() {
        mInfoUserViewModel.getAcademicAdvanceList().observe(viewLifecycleOwner, { academicAdvance ->
            val adapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, academicAdvance)

            with(mBinding) {
                atvAcademicAdvance.setAdapter(adapter)
                atvAcademicAdvance.setOnItemClickListener { parent, _, position, id ->
                    mAcademicAdvance = parent.getItemAtPosition(position).toString()

                    llPeriod.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setupTextFields() {
        with(mBinding) {

            val fields = arrayListOf(
                tilAcademicLevel, tilSchool, tilAcademicAdvance
            )

            fields.forEach { textInputLayout ->
                textInputLayout.editText?.addTextChangedListener {
                    validateFields(
                        textInputLayout,
                        fab = fabNext,
                        context = requireContext()
                    )
                }
            }

            fabReturn.setOnClickListener {
                listener?.onSelectStepView(0, R.id.personalFragment)
                sendDataOptionFragment(false)
            }
            fabNext.setOnClickListener {
                sendDataOptionFragment(true)
            }
        }
    }

    private fun sendDataOptionFragment(isSave: Boolean) {
        if (validateFields(
                tilAcademicAdvance,
                tilSchool,
                tilAcademicLevel,
                fab = fabNext,
                context = requireContext()
            )
        ) {
            mInfoUserViewModel.setAcademicUser(
                AcademicUser(
                    levelAcademic = atvAcademicLevel.text.toString().trim(),
                    school = etSchool.text.toString().trim(),
                    academicAdvance = atvAcademicAdvance.text.toString().trim()
                )
            )

            if (isSave) {
                if (atvAcademicLevel.text.toString() == "Universidad") {
                    listener?.onSelectStepView(1, R.id.postgraduateFragment)
                } else {
                    listener?.onSelectStepView(2, R.id.workExperienceFragment)
                }
                addSelectData()
            }
        }
    }

    fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    listener?.onSelectStepView(0, R.id.personalFragment)
                    sendDataOptionFragment(false)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onStart() {
        super.onStart()
        addSelectData()
    }
}