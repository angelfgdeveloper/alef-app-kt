package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.personal

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.Validators.Companion.onBackPress
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.Validators.Companion.validateFields
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentPersonalBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUser
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel
import kotlinx.android.synthetic.main.fragment_personal.*

class PersonalFragment : Fragment(R.layout.fragment_personal) {

    private lateinit var mBinding: FragmentPersonalBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()

    private var listener: StepViewListener? = null

    private var mGender = ""
    private var mState = ""
    private var mTown = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPersonalBinding.bind(view)

        onBackPress(requireActivity())
        addSelectData()
        setupTextFields()

        // TODO: Borrar test -> setText("...");
        with(mBinding) {
            etLastName.setText("Crown")
            etMotherLastName.setText("Lower")
            etName.setText("Lorrey")
            etDateOfBirth.setText("10/12/1994")
            atvGender.setText("Otro")
            atvState.setText("Durango")
            atvTwon.setText("Durango")
            etSuburb.setText("Blvd. Felipe")
            etStreet.setText("Calle 123")
            etNumberHome.setText("1542")
            etCP.setText("34000")
            etTelephone.setText("")
            etMobile.setText("6188143030")
        }
        // TODO: ....

        mInfoUserViewModel.getInfoUser().observe(viewLifecycleOwner, { user ->
            with(mBinding) {
                etLastName.setText(user.lastName)
                etMotherLastName.setText(user.motherLastName)
                etName.setText(user.name)
                etDateOfBirth.setText(user.dateOfBirth)
                atvGender.setText(user.gender)
                atvState.setText(user.state)
                atvTwon.setText(user.town)
                etSuburb.setText(user.suburb)
                etStreet.setText(user.street)
                etNumberHome.setText(user.numberHome.toString())
                etCP.setText(user.postalCode.toString())
                etTelephone.setText(user.telephone)
                etMobile.setText(user.cellphone)
            }
            addSelectData()
        })

    }

    private fun addSelectData() {
        mInfoUserViewModel.getGenderList().observe(viewLifecycleOwner, { gender ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, gender)

            with(mBinding) {
                atvGender.setAdapter(adapter)
                atvGender.setOnItemClickListener { parent, _, position, _ ->
                    mGender = parent.getItemAtPosition(position).toString()
//                    Toast.makeText(
//                        activity, "$mGender - Posicion: $position id: $id", Toast.LENGTH_SHORT
//                    ).show()
                }
            }
        })

        mInfoUserViewModel.getStateList().observe(viewLifecycleOwner, { state ->
            val adapterState = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, state)

            with(mBinding) {
                atvState.setAdapter(adapterState)
                atvState.setOnItemClickListener { parent, _, position, _ ->
                    mState = parent.getItemAtPosition(position).toString()

                    if (mState.isNotEmpty()) {
                        mInfoUserViewModel.getTownList().observe(viewLifecycleOwner, { town ->
                            val adapterTown =
                                ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, town)

                            with(mBinding) {
                                atvTwon.setAdapter(adapterTown)
                                atvTwon.setOnItemClickListener { parent, _, position, _ ->
                                    mTown = parent.getItemAtPosition(position).toString()
                                }
                            }
                        })
                    }

                }
            }
        })

        if (atvState.text.toString().isNotEmpty()) {
            mInfoUserViewModel.getTownList().observe(viewLifecycleOwner, { town ->
                val adapterTown = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, town)

                with(mBinding) {
                    atvTwon.setAdapter(adapterTown)
                    atvTwon.setOnItemClickListener { parent, _, position, _ ->
                        mTown = parent.getItemAtPosition(position).toString()
                    }
                }
            })
        }

    }

    private fun setupTextFields() {

        with(mBinding) {

            val fields = arrayListOf(
                tilLastName, tilMotherLastName, tilName, tilDateOfBirth, tilGender, tilState,
                tilTown, tilSuburb, tilStreet, tilNumberHome, tilCP, tilMobile
            )

            fields.forEach { textInputLayout ->
                textInputLayout.editText?.addTextChangedListener {
                    validateFields(textInputLayout, fab = fabNext, context = requireContext())
                }
            }

            etMobile.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    goToAcademic()
                    handled = true
                }
                handled
            }

            fabReturn.setOnClickListener { requireActivity().finish() }
            fabNext.setOnClickListener { goToAcademic() }
        }
    }

    private fun goToAcademic() {
        if (validateFields(
                tilMobile,
                tilCP,
                tilNumberHome,
                tilStreet,
                tilSuburb,
                tilTown,
                tilState,
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
                    state = atvState.text.toString().trim(),
                    town = atvTwon.text.toString().trim(),
                    suburb = etSuburb.text.toString().trim(),
                    street = etStreet.text.toString().trim(),
                    numberHome = etNumberHome.text.toString().toInt(),
                    postalCode = etCP.text.toString().toInt(),
                    telephone = etTelephone.text.toString().trim(),
                    cellphone = etMobile.text.toString().trim(),
                )
            )

            listener?.onSelectStepView(1, R.id.academicFragment)
// //               findNavController().navigate(R.id.action_personalFragment_to_academicFragment)
        }
    }

//    private fun hideKeyboard() {
//        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
//        if (view != null) {
//            imm?.hideSoftInputFromWindow(requireView().windowToken, 0)
//        }
//    }

    override fun onStart() {
        super.onStart()
        addSelectData()
    }
}