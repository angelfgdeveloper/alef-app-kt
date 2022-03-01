package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.academic

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
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

    private var mCertificate: Boolean? = null
    private var mTitleAchieved: Boolean? = null
    private var mIdentificationCard: Boolean? = null

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

        // TODO: Borrar test -> setText("...");
        with(mBinding) {
            atvAcademicLevel.setText("Primaria")
            etSchool.setText("Instituto Tecnólogico de Durango")
            atvAcademicAdvance.setText("Terminado")
            atvStartMonth.setText("Junio")
            etStartYear.setText("2013")
            atvEndMonth.setText("Junio")
            etEndYear.setText("2020")
            rbYesCertificate.isChecked = true
            rbNoCertificate.isChecked = false
            rbYesTitleAchieved.isChecked = true
            rbNoTitleAchieved.isChecked = false
            rbYesIdentificationCard.isChecked = true
            rbNoIdentificationCard.isChecked = false
            llAcademic.visibility = View.VISIBLE
            llPeriod.visibility = View.VISIBLE
            llCertificated.visibility = View.VISIBLE
            fabNext.isEnabled = true
        }
        // TODO: ....

        mInfoUserViewModel.getAcademicUser().observe(viewLifecycleOwner) { academicUser ->
            with(mBinding) {
                atvAcademicLevel.setText(academicUser?.levelAcademic)
                if (atvAcademicLevel.text.toString().isNotEmpty()) {
                    llAcademic.visibility = View.VISIBLE

                    etSchool.setText(academicUser?.school)
                    atvAcademicAdvance.setText(academicUser?.academicAdvance)

                    atvAcademicLevel.setText(academicUser.levelAcademic)

                    if (atvAcademicLevel.text.isNotEmpty()) {
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

                            llPeriod.visibility = View.VISIBLE
                            atvStartMonth.setText(academicUser?.startMonth)
                            etStartYear.setText(academicUser?.startYear.toString())
                            atvEndMonth.setText(academicUser?.endMonth)
                            etEndYear.setText(academicUser?.endYear.toString())
                            fabNext.isEnabled = true

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

                                            if (academicUser.certificate) {
                                                rbYesCertificate.isChecked = true
                                                rbNoCertificate.isChecked = false
                                            } else {
                                                rbYesCertificate.isChecked = false
                                                rbNoCertificate.isChecked = true
                                            }

                                            if (academicUser.titleAchieved) {
                                                rbYesTitleAchieved.isChecked = true
                                                rbNoTitleAchieved.isChecked = false
                                            } else {
                                                rbYesTitleAchieved.isChecked = false
                                                rbNoTitleAchieved.isChecked = true
                                            }

                                            if (academicUser.identificationCard) {
                                                rbYesIdentificationCard.isChecked = true
                                                rbNoIdentificationCard.isChecked = false
                                            } else {
                                                rbYesIdentificationCard.isChecked = false
                                                rbNoIdentificationCard.isChecked = true
                                            }

                                        }
                                    }
                                }
                            }

                            llPeriod.visibility = View.VISIBLE
                            atvStartMonth.setText(academicUser?.startMonth)
                            etStartYear.setText(academicUser?.startYear.toString())
                            atvEndMonth.setText(academicUser?.endMonth)
                            etEndYear.setText(academicUser?.endYear.toString())

                            fabNext.isEnabled = true

                            addMonths()
                        }
                    } else {
                        llPeriod.visibility = View.GONE
                        atvStartMonth.setText("")
                        etStartYear.setText("")
                        atvEndMonth.setText("")
                        etEndYear.setText("")
                        llCertificated.visibility = View.GONE
                        rgCertificate.clearCheck()
                        rgTitleAchieved.clearCheck()
                        rgIdentificationCard.clearCheck()
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
                    rgCertificate.clearCheck()
                    rgTitleAchieved.clearCheck()
                    rgIdentificationCard.clearCheck()
                }
            }
        }

        val academicLevel = mBinding.atvAcademicLevel.text.toString().trim()
        val academicAdvance = mBinding.atvAcademicAdvance.text.toString().trim()

        if (academicLevel.isNotEmpty()) {
            addMonths()

            if (academicAdvance.isNotEmpty()) {
                when (academicLevel) {
                    "Primaria", "Secundaria" -> {
                        setAcademicAdvance(false)
                    }
                    "Bachillerato", "Universidad" -> {
                        setAcademicAdvance(true)
                    }
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
                    rgCertificate.clearCheck()
                    rgTitleAchieved.clearCheck()
                    rgIdentificationCard.clearCheck()

                    atvStartMonth.setText("")
                    etStartYear.setText("")
                    atvEndMonth.setText("")
                    etEndYear.setText("")

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

            rgCertificate.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbYesCertificate -> {
                        mCertificate = true
                    }
                    R.id.rbNoCertificate -> {
                        mCertificate = false
                    }
                }
            }

            rgTitleAchieved.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbYesTitleAchieved -> {
                        mTitleAchieved = true
                    }
                    R.id.rbNoTitleAchieved -> {
                        mTitleAchieved = false
                    }
                }
            }

            rgIdentificationCard.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbYesIdentificationCard -> {
                        mIdentificationCard = true
                    }
                    R.id.rbNoIdentificationCard -> {
                        mIdentificationCard = false
                    }
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
                        if (mCertificate != null &&
                            mTitleAchieved != null &&
                            mIdentificationCard != null
                        ) {
                            if (mIdentificationCard == true && atvAcademicLevel.text.toString()
                                    .trim() == "Universidad"
                            ) {
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
                                        certificate = mCertificate!!,
                                        titleAchieved = mTitleAchieved!!,
                                        identificationCard = mIdentificationCard!!
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
                                        certificate = mCertificate!!,
                                        titleAchieved = mTitleAchieved!!,
                                        identificationCard = mIdentificationCard!!
                                    )
                                )

                                listener?.onSelectStepView(2, R.id.workExperienceFragment)
                            }

                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Por favor, complete los datos restantes",
                                Toast.LENGTH_SHORT
                            ).show()
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
                        ) 0 else etEndYear.text.toString().toInt(),
                        certificate = mCertificate ?: false,
                        titleAchieved = mTitleAchieved ?: false,
                        identificationCard = mIdentificationCard ?: false
                    )
                )
            } else {
                rgCertificate.clearCheck()
                rgTitleAchieved.clearCheck()
                rgIdentificationCard.clearCheck()

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