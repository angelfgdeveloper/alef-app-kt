package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.academic

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.Validators.Companion.validateFields
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

        mInfoUserViewModel.getAcademicUser().observe(viewLifecycleOwner) { academicUser ->
            with(mBinding) {
                atvAcademicLevel.setText(academicUser?.levelAcademic)
                if (atvAcademicLevel.text.toString().isNotEmpty()) {
                    llAcademic.visibility = View.VISIBLE

                    etSchool.setText(academicUser?.school)
                    atvAcademicAdvance.setText(academicUser?.academicAdvance)

                    atvAcademicLevel.setText(academicUser.levelAcademic)

                    if (atvAcademicLevel.text.isNotEmpty()) {
                        llPeriod.visibility = View.VISIBLE
                        if (atvAcademicLevel.text.toString() == "Primaria" ||
                            atvAcademicLevel.text.toString() == "Secundaria"
                        ) {
                            setAcademicAdvance(false)
                            val academicLevel = atvAcademicLevel.text.toString()
                            when (atvAcademicAdvance.text.toString()) {
                                "En curso", "Trunca", "Terminado" -> {
                                    llCertificated.visibility = View.GONE
                                }
                                "Grado técnico" -> {
                                    when (academicLevel) {
                                        "Bachillerato", "Universidad" -> {
                                            llCertificated.visibility = View.VISIBLE
                                        }
                                    }
                                }
                            }

                            atvStartMonth.setText(academicUser?.startMonth)
                            etStartYear.setText(academicUser?.startYear.toString())
                            atvEndMonth.setText(academicUser?.endMonth)
                            etEndYear.setText(academicUser?.endYear.toString())

                            addMonths()
                        } else {
                            setAcademicAdvance(true)
                            val academicLevel = atvAcademicLevel.text.toString()
                            when (atvAcademicAdvance.text.toString()) {
                                "En curso", "Trunca", "Terminado" -> {
                                    llCertificated.visibility = View.GONE
                                }
                                "Grado técnico" -> {
                                    when (academicLevel) {
                                        "Bachillerato", "Universidad" -> {
                                            llCertificated.visibility = View.VISIBLE
                                        }
                                    }
                                }
                            }

                            atvStartMonth.setText(academicUser?.startMonth)
                            etStartYear.setText(academicUser?.startYear.toString())
                            atvEndMonth.setText(academicUser?.endMonth)
                            etEndYear.setText(academicUser?.endYear.toString())

                            addMonths()
                        }
                    } else {
                        llPeriod.visibility = View.GONE
                        atvStartMonth.setText("")
                        etStartYear.setText("")
                        atvEndMonth.setText("")
                        etEndYear.setText("")
                        llCertificated.visibility = View.GONE
                    }

                } else {
                    llAcademic.visibility = View.GONE
                    etSchool.setText("")
                    atvAcademicAdvance.setText("")
                    atvAcademicLevel.setText("")
                    llPeriod.visibility = View.GONE
                    atvStartMonth.setText("")
                    etStartYear.setText("")
                    atvEndMonth.setText("")
                    etEndYear.setText("")
                    llCertificated.visibility = View.GONE
                }
            }
        }
    }

    private fun addSelectData() {
        mInfoUserViewModel.getAcademicLevelList().observe(viewLifecycleOwner) { academicLevel ->
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

                    if (mAcademicLevel == "Primaria") {
                        setAcademicAdvance(false)
                    } else if (mAcademicLevel == "Secundaria") {
                        setAcademicAdvance(false)
                    } else {
                        setAcademicAdvance(true)
                    }

                }
            }
        }

    }

    private fun setAcademicAdvance(isHigher: Boolean) {
        mInfoUserViewModel.getAcademicAdvanceList().observe(viewLifecycleOwner) { academicAdvance ->
            val academicList = mutableListOf<String>()

            if (isHigher) {
                academicList.addAll(academicAdvance)
            } else {
                academicList.addAll(academicAdvance)
                academicList.removeFirst()
                academicList.removeLast()
            }

            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, academicList)

            with(mBinding) {
                atvAcademicAdvance.setAdapter(adapter)
                atvAcademicAdvance.setOnItemClickListener { parent, _, position, id ->
                    mAcademicAdvance = parent.getItemAtPosition(position).toString()
                    llPeriod.visibility = View.VISIBLE

                    atvStartMonth.setText("")
                    etStartYear.setText("")
                    atvEndMonth.setText("")
                    etEndYear.setText("")

                    val academicLevel = atvAcademicLevel.text.toString()
                    when (mAcademicAdvance) {
                        "En curso", "Trunca", "Terminado" -> {
                            llCertificated.visibility = View.GONE
                        }
                        "Grado técnico" -> {
                            when (academicLevel) {
                                "Bachillerato", "Universidad" -> {
                                    llCertificated.visibility = View.VISIBLE
                                }
                            }
                        }
                    }

                    fabNext.isEnabled = true
                    addMonths()
                }
            }

        }
    }

    private fun addMonths() {
        mInfoUserViewModel.getMonthList().observe(viewLifecycleOwner) { month ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, month)

            with(mBinding) {
                atvStartMonth.setAdapter(adapter)
                atvStartMonth.setOnItemClickListener { parent, _, position, id ->
                    mStartMonth = parent.getItemAtPosition(position).toString()
                    fabNext.isEnabled = true
                }

                atvEndMonth.setAdapter(adapter)
                atvEndMonth.setOnItemClickListener { parent, _, position, id ->
                    mEndMonth = parent.getItemAtPosition(position).toString()
                    fabNext.isEnabled = true
                }

            }

        }
    }

    private fun setupTextFields() {
        with(mBinding) {
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
                val fields = arrayListOf(
                    tilEndYear,
                    tilStartYear,
                    tilSchool,
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

                if (atvAcademicAdvance.text.toString() == "Grado técnico") {
                    with(mBinding) {

                        var certificate = false
                        if (rbYesCertificate.isChecked) {
                            certificate = true
                        } else if (rbNoCertificate.isChecked) {
                            certificate = false
                        }

                        var titleAchieved = false
                        if (rbYesTitleAchieved.isChecked) {
                            titleAchieved = true
                        } else if (rbNoTitleAchieved.isChecked) {
                            titleAchieved = false
                        }

                        var identificationCard = false
                        if (rbYesIdentificationCard.isChecked) {
                            identificationCard = true
                        } else if (rbNoIdentificationCard.isChecked) {
                            identificationCard = false
                        }

                        if (certificate || titleAchieved || identificationCard) {

                            if (rbYesIdentificationCard.isChecked) {
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
                                        certificate = certificate,
                                        titleAchieved = titleAchieved,
                                        identificationCard = true
                                    )
                                )

                                listener?.onSelectStepView(1, R.id.postgraduateFragment)
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
                                        certificate = certificate,
                                        titleAchieved = titleAchieved,
                                        identificationCard = false
                                    )
                                )

                                listener?.onSelectStepView(2, R.id.workExperienceFragment)
                            }

                        }
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
                            ) 0 else etEndYear.text.toString().toInt()
                        )
                    )
                    listener?.onSelectStepView(2, R.id.workExperienceFragment)
                }

            }
        } else {
            if (atvAcademicAdvance.text.toString() == "Grado técnico") {
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
                        ) 0 else etEndYear.text.toString().toInt()
                    )
                )
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
                        ) 0 else etEndYear.text.toString().toInt()
                    )
                )
            }
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onStart() {
        super.onStart()
        addSelectData()
    }
}