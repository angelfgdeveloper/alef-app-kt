package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.personal

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.core.Validators.Companion.validateFields
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentPersonalBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.InformationUserActivity
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUser
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel

class PersonalFragment : Fragment(R.layout.fragment_personal) {

    private lateinit var mBinding: FragmentPersonalBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()

    private var mActivity: InformationUserActivity? = null
    private var listener: StepViewListener? = null

    private var mGender = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPersonalBinding.bind(view)

        onBackPress()
        addSelectData()
        setupTextFields()

        mInfoUserViewModel.getInfoUser().observe(viewLifecycleOwner, { user ->
            with(mBinding) {
                etLastName.setText(user.lastName)
                etMotherLastName.setText(user.motherLastName)
                etName.setText(user.name)
                etDateOfBirth.setText(user.dateOfBirth)
                atvGender.setText(user.gender)
                etTelephone.setText(user.telephone)
                etMobile.setText(user.cellphone)
                etAddress.setText(user.address)
                etCurp.setText(user.curp)
                etRfc.setText(user.rfc)
                etNss.setText(user.nss)
            }
        })
    }

    private fun addSelectData() {
        mInfoUserViewModel.getGenderList().observe(viewLifecycleOwner, { gender ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, gender)

            with(mBinding) {
                atvGender.setAdapter(adapter)
                atvGender.setOnItemClickListener { parent, _, position, id ->
                    mGender = parent.getItemAtPosition(position).toString()

                    Toast.makeText(
                        activity, "$mGender - Posicion: $position id: $id", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun setupTextFields() {
        with(mBinding) {
            etLastName.addTextChangedListener {
                validateFields(tilLastName, fab = fabNext, context = requireContext())
            }
            etMotherLastName.addTextChangedListener {
                validateFields(tilMotherLastName, fab = fabNext, context = requireContext())
            }
            etName.addTextChangedListener {
                validateFields(tilName, fab = fabNext, context = requireContext())
            }
            etDateOfBirth.addTextChangedListener {
                validateFields(tilDateOfBirth, fab = fabNext, context = requireContext())
            }
            atvGender.addTextChangedListener {
                validateFields(tilGender, fab = fabNext, context = requireContext())
            }
            etMobile.addTextChangedListener {
                validateFields(tilMobile, fab = fabNext, context = requireContext())
            }
            etAddress.addTextChangedListener {
                validateFields(tilAddress, fab = fabNext, context = requireContext())
            }
            etCurp.addTextChangedListener {
                validateFields(tilCurp, fab = fabNext, context = requireContext())
            }
            etRfc.addTextChangedListener {
                validateFields(tilRfc, fab = fabNext, context = requireContext())
            }
            etNss.addTextChangedListener {
                validateFields(tilNss, fab = fabNext, context = requireContext())
            }

            fabReturn.setOnClickListener { requireActivity().finish() }
            fabNext.setOnClickListener {
                if (validateFields(
                        tilNss,
                        tilRfc,
                        tilCurp,
                        tilAddress,
                        tilMobile,
                        tilGender,
                        tilDateOfBirth,
                        tilName,
                        tilMotherLastName,
                        tilLastName,
                        fab = fabNext,
                        context = requireContext()
                    )
                ) {
                    mInfoUserViewModel.setInfoUser(
                        InfoUser(
                            lastName = etLastName.text.toString().trim(),
                            motherLastName = etMotherLastName.text.toString().trim(),
                            name = etName.text.toString().trim(),
                            dateOfBirth = etDateOfBirth.text.toString().trim(),
                            gender = atvGender.text.toString().trim(),
                            state = "",
                            town = "",
                            telephone = etTelephone.text.toString().trim(),
                            cellphone = etMobile.text.toString().trim(),
                            address = etAddress.text.toString().trim(),
                            curp = etCurp.text.toString().trim(),
                            rfc = etRfc.text.toString().trim(),
                            nss = etNss.text.toString().trim()
                        )
                    )

                    listener?.onSelectStepView(1, R.id.academicFragment)
//                findNavController().navigate(R.id.action_personalFragment_to_academicFragment)
                }
            }
        }
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

    override fun onStart() {
        super.onStart()
        addSelectData()
    }
}