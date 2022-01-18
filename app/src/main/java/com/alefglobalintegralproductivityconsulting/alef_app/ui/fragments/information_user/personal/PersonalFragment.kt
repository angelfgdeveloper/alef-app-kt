package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.personal

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentPersonalBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.InformationUserActivity
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.InfoUser
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.InfoUserViewModel
import com.google.android.material.textfield.TextInputLayout

class PersonalFragment : Fragment(R.layout.fragment_personal) {

    private lateinit var mBinding: FragmentPersonalBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()

    private var mActivity: InformationUserActivity? = null
    private var listener: StepViewListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPersonalBinding.bind(view)

        setupTextFields()
        onBackPress()

        mInfoUserViewModel.getInfoUser().observe(viewLifecycleOwner, { user ->
            mBinding.etName.setText(user.name)
        })
    }

    private fun setupTextFields() {
        with(mBinding) {
            etName.addTextChangedListener { validateFields(tilName) }

            fabReturn.setOnClickListener { requireActivity().finish() }
            fabNext.setOnClickListener {
                mInfoUserViewModel.setInfoUser(
                    InfoUser(
                        "Fly",
                        "flow",
                        etName.text.toString().trim()
                    )
                )

                listener?.onSelectStepView(1, R.id.academicFragment)
//                findNavController().navigate(R.id.action_personalFragment_to_academicFragment)
            }
        }
    }

    private fun validateFields(vararg textFields: TextInputLayout): Boolean {
        var isValid = true

        for (textField in textFields) {
            if (textField.editText?.text.toString().trim().isEmpty()) {
                textField.error = getString(R.string.helper_required)
                textField.editText?.requestFocus()
                isValid = false
            } else textField.error = null
        }

        mBinding.fabNext.isEnabled = isValid
        return isValid
    }

    private fun hideKeyboard() {
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (view != null) {
            imm?.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}