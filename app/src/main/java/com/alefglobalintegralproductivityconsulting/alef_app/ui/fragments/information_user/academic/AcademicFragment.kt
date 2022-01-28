package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.academic

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
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
    private var mStartMonth = ""
    private var mEndMonth = ""

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

                    setAcademicAdvance(false)

                    if (atvAcademicAdvance.text.toString().isNotEmpty()) {
                        llPeriod.visibility = View.VISIBLE

                        atvStartMonth.setText(academicUser?.startMonth)
                        etStartYear.setText(
                            if (academicUser?.startYear.toString()
                                    .toInt() == 0
                            ) "" else academicUser?.startYear.toString()
                        )

                        atvEndMonth.setText(academicUser?.endMonth)
                        etEndYear.setText(
                            if (academicUser?.endYear.toString()
                                    .toInt() == 0
                            ) "" else academicUser?.endYear.toString()
                        )

                        addMonths()
                    } else {
                        llPeriod.visibility = View.GONE

                        atvStartMonth.setText("")
                        etStartYear.setText("")
                        etEndYear.setText("")
                        atvEndMonth.setText("")

                        addMonths()
                    }

                    if (atvAcademicAdvance.text.toString() == "Terminado" ||
                        atvAcademicAdvance.text.toString() == "Terminado y con cédula"
                    ) {
                        llCertificated.visibility = View.VISIBLE
                        etCertificate.setText(academicUser?.certificate.toString())
                        etTitleAchieved.setText(academicUser?.titleAchieved.toString())
                        etIdentificationCard.setText(academicUser?.identificationCard.toString())
                    } else if (atvAcademicLevel.text.toString() == "Primaria") {
                        llCertificated.visibility = View.GONE
                        etCertificate.setText("")
                        etTitleAchieved.setText("")
                        etIdentificationCard.setText("")
                    } else {
                        llCertificated.visibility = View.GONE
                        etCertificate.setText("")
                        etTitleAchieved.setText("")
                        etIdentificationCard.setText("")
                    }

                } else {
                    llAcademic.visibility = View.GONE
                    etSchool.setText("")
                    atvAcademicAdvance.setText("")

                    llPeriod.visibility = View.GONE
                    atvStartMonth.setText("")
                    etStartYear.setText("")
                    etEndYear.setText("")
                    atvEndMonth.setText("")

                    llCertificated.visibility = View.GONE
                    etCertificate.setText("")
                    etTitleAchieved.setText("")
                    etIdentificationCard.setText("")
                }

            }
        })
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
                    llCertificated.visibility = View.GONE

                    etSchool.setText("")
                    atvAcademicAdvance.setText("")

                    atvStartMonth.setText("")
                    etStartYear.setText("")
                    etEndYear.setText("")
                    atvEndMonth.setText("")

                    etCertificate.setText("")
                    etTitleAchieved.setText("")
                    etIdentificationCard.setText("")



                    if (atvAcademicLevel.text.toString() == "Primaria" ||
                        atvAcademicLevel.text.toString() == "Secundaria"
                    ) {
                        setAcademicAdvance(false)
                    } else {
                        setAcademicAdvance(true)
                    }
                }
            }
        })

    }

    private fun setAcademicAdvance(isHigher: Boolean) {

        mInfoUserViewModel.getAcademicAdvanceList().observe(viewLifecycleOwner, { academicAdvance ->
            val academicList = mutableListOf<String>()

            if (isHigher) {
                academicList.addAll(academicAdvance)
            } else {
                academicList.addAll(academicAdvance)
                academicList.removeLast()
            }

            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, academicList)

            with(mBinding) {
                atvAcademicAdvance.setAdapter(adapter)
                atvAcademicAdvance.setOnItemClickListener { parent, _, position, id ->
                    mAcademicAdvance = parent.getItemAtPosition(position).toString()
                    llPeriod.visibility = View.VISIBLE

                    addMonths()

                    atvStartMonth.setText("")
                    etStartYear.setText("")
                    etEndYear.setText("")
                    atvEndMonth.setText("")

                    if (mAcademicAdvance == "Terminado" || mAcademicAdvance == "Terminado y con cédula") {
                        llCertificated.visibility = View.VISIBLE
                    } else if (atvAcademicLevel.text.toString() == "Primaria") {
                        llCertificated.visibility = View.GONE
                        etCertificate.setText("")
                        etTitleAchieved.setText("")
                        etIdentificationCard.setText("")
                    } else {
                        llCertificated.visibility = View.GONE
                        etCertificate.setText("")
                        etTitleAchieved.setText("")
                        etIdentificationCard.setText("")
                    }

                }
            }

        })
    }

    private fun addMonths() {
        mInfoUserViewModel.getMonthList().observe(viewLifecycleOwner, { month ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, month)

            with(mBinding) {
                atvStartMonth.setAdapter(adapter)
                atvStartMonth.setOnItemClickListener { parent, _, position, id ->
                    mStartMonth = parent.getItemAtPosition(position).toString()
                }

                atvEndMonth.setAdapter(adapter)
                atvEndMonth.setOnItemClickListener { parent, _, position, id ->
                    mEndMonth = parent.getItemAtPosition(position).toString()
                }
            }

        })
    }

    private fun setupTextFields() {
        with(mBinding) {

            val fields = arrayListOf(
                tilEndYear,
                tilEndMonth,
                tilStartYear,
                tilStartMonth,
                tilAcademicAdvance,
                tilSchool,
                tilAcademicLevel,
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
        if (isSave) {
            if (validateFields(
                    tilEndYear,
                    tilEndMonth,
                    tilStartYear,
                    tilStartMonth,
                    tilAcademicAdvance,
                    tilSchool,
                    tilAcademicLevel,
                    fab = fabNext,
                    context = requireContext()
                )
            ) {

                if ((atvAcademicLevel.text.toString() == "Universidad" ||
                            atvAcademicLevel.text.toString() == "Bachillerato") &&
                    atvAcademicAdvance.text.toString() == "Terminado y con cédula"
                ) {

                    val fields = arrayListOf(
                        tilIdentificationCard,
                        tilTitleAchieved,
                        tilCertificate
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

                    if (validateFields(
                            tilIdentificationCard,
                            tilTitleAchieved,
                            tilCertificate,
                            tilEndYear,
                            tilEndMonth,
                            tilStartYear,
                            tilStartMonth,
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
                                academicAdvance = atvAcademicAdvance.text.toString().trim(),
                                startMonth = atvStartMonth.text.toString().trim(),
                                startYear = etStartYear.text.toString().toInt(),
                                endMonth = atvEndMonth.text.toString().trim(),
                                endYear = etEndYear.text.toString().toInt(),
                                certificate = etCertificate.text.toString(),
                                titleAchieved = etTitleAchieved.text.toString(),
                                identificationCard = etIdentificationCard.text.toString()
                            )
                        )

                        listener?.onSelectStepView(1, R.id.postgraduateFragment)
                    }

                } else if (
                    (atvAcademicLevel.text.toString() != "Primaria" ||
                            atvAcademicLevel.text.toString() != "Secundaria") &&
                    atvAcademicAdvance.text.toString() == "Terminado"
                ) {
                    val fields = arrayListOf(
                        tilTitleAchieved,
                        tilCertificate
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

                    if (validateFields(
                            tilTitleAchieved,
                            tilCertificate,
                            tilEndYear,
                            tilEndMonth,
                            tilStartYear,
                            tilStartMonth,
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
                                academicAdvance = atvAcademicAdvance.text.toString().trim(),
                                startMonth = atvStartMonth.text.toString().trim(),
                                startYear = etStartYear.text.toString().toInt(),
                                endMonth = atvEndMonth.text.toString().trim(),
                                endYear = etEndYear.text.toString().toInt(),
                                certificate = etCertificate.text.toString(),
                                titleAchieved = etTitleAchieved.text.toString(),
                            )
                        )

                        listener?.onSelectStepView(2, R.id.workExperienceFragment)
                    }

                } else {
                    mInfoUserViewModel.setAcademicUser(
                        AcademicUser(
                            levelAcademic = atvAcademicLevel.text.toString().trim(),
                            school = etSchool.text.toString().trim(),
                            academicAdvance = atvAcademicAdvance.text.toString().trim(),
                            startMonth = atvStartMonth.text.toString().trim(),
                            startYear = etStartYear.text.toString().toInt(),
                            endMonth = atvEndMonth.text.toString().trim(),
                            endYear = etEndYear.text.toString().toInt()
                        )
                    )

                    listener?.onSelectStepView(2, R.id.workExperienceFragment)
                }
                addSelectData()
            }
        } else {
            mInfoUserViewModel.setAcademicUser(
                AcademicUser(
                    levelAcademic = atvAcademicLevel.text.toString().trim(),
                    school = etSchool.text.toString().trim(),
                    academicAdvance = atvAcademicAdvance.text.toString().trim(),
                    startMonth = atvStartMonth.text.toString().trim(),
                    startYear = if (etStartYear.text.toString()
                            .isEmpty()
                    ) 0 else etStartYear.text.toString().toInt(),
                    endMonth = atvEndMonth.text.toString().trim(),
                    endYear = if (etEndYear.text.toString()
                            .isEmpty()
                    ) 0 else etEndYear.text.toString().toInt(),
                    certificate = etCertificate.text.toString(),
                    titleAchieved = etTitleAchieved.text.toString(),
                    identificationCard = etIdentificationCard.text.toString()
                )
            )
        }
    }

    private fun onBackPress() {
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