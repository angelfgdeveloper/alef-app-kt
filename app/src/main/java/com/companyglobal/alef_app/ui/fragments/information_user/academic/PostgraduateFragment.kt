package com.companyglobal.alef_app.ui.fragments.information_user.academic

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.StepViewListener
import com.companyglobal.alef_app.core.utils.Validators.Companion.addAllMonths
import com.companyglobal.alef_app.core.utils.Validators.Companion.validateFields
import com.companyglobal.alef_app.databinding.FragmentPostgraduateBinding
import com.companyglobal.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel
import com.companyglobal.alef_app.ui.fragments.information_user.viewmodel.Posgraduate
import com.companyglobal.alef_app.ui.fragments.information_user.viewmodel.PosgraduateUser
import com.companyglobal.alef_app.ui.fragments.information_user.viewmodel.STATE
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_postgraduate.*

class PostgraduateFragment : Fragment(R.layout.fragment_postgraduate) {

    private lateinit var mBinding: FragmentPostgraduateBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()
    private var listener: StepViewListener? = null

    private var mNumberPosgraduate: String = ""
    private var mState: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPostgraduateBinding.bind(view)

        mInfoUserViewModel.getPosgraduateUser().observe(viewLifecycleOwner) { posgraduateUser ->
            with(mBinding) {
                when (posgraduateUser.state) {
                    STATE.NO -> {
                        rbNoPosgraduate.isChecked = true
                        for (i in 1..4) {
                            cleanAllAutocompleteTextView("atvType$i")
                            cleanAllAutocompleteTextView("atvStartMonth$i")
                            cleanAllAutocompleteTextView("atvEndMonth$i")
                            cleanAllTextInputEditText("etTitleAchieved$i")
                            cleanAllTextInputEditText("etStartYear$i")
                            cleanAllTextInputEditText("etEndYear$i")
                        }
                    }
                    STATE.YES -> {
                        rbYesPosgraduate.isChecked = true

                        if (posgraduateUser.total > 0) {

                            for (pg in 1..posgraduateUser.total) {
                                addDataAllTextInputEditText(
                                    "etTitleAchieved$pg",
                                    posgraduateUser.totalPosgraduate[pg - 1].title
                                )

                                addDataAllTextInputEditText(
                                    "etStartYear$pg",
                                    posgraduateUser.totalPosgraduate[pg - 1].startYear.toString()
                                )

                                addDataAllTextInputEditText(
                                    "etEndYear$pg",
                                    posgraduateUser.totalPosgraduate[pg - 1].endYear.toString()
                                )

                                visibilityLinearLayout("llPosgraduate$pg", View.VISIBLE)

                                atvPosgraduate.setText(posgraduateUser.total.toString())

                                addDataAllAutocompleteTextView(
                                    "atvType$pg",
                                    posgraduateUser.totalPosgraduate[pg - 1].type
                                )

                                addDataAllAutocompleteTextView(
                                    "atvStartMonth$pg",
                                    posgraduateUser.totalPosgraduate[pg - 1].startMonth
                                )

                                addDataAllAutocompleteTextView(
                                    "atvEndMonth$pg",
                                    posgraduateUser.totalPosgraduate[pg - 1].endMonth
                                )
                            }
                        }
                        setData()
                    }
                    STATE.IN_PROCESS -> {
                        rbInProgressPosgraduate.isChecked = true
                        atvType1.setText(posgraduateUser.totalPosgraduate[0].type)
                        etTitleAchieved1.setText(posgraduateUser.totalPosgraduate[0].title)
                        atvStartMonth1.setText(posgraduateUser.totalPosgraduate[0].startMonth)
                        etStartYear1.setText(posgraduateUser.totalPosgraduate[0].startYear.toString())
                        atvEndMonth1.setText(posgraduateUser.totalPosgraduate[0].endMonth)
                        etEndYear1.setText(posgraduateUser.totalPosgraduate[0].endYear.toString())

                        setData()
                    }
                }
            }
        }

        onBackPress()
        setupTextFields()
        addSelectData()
    }

    private fun setupTextFields() {
        with(mBinding) {

            rgPosgraduate.setOnCheckedChangeListener { _, checkedId ->
                val linearLayoutList = listOf(llPosgraduates)
                val textInputLayoutList = listOf(tilPosgraduate)
                val autoCompleteTextViewList = listOf(atvPosgraduate)

                when (checkedId) {
                    R.id.rbYesPosgraduate -> {
                        setupStatus(STATE.YES.state, linearLayoutList, textInputLayoutList)
                    }
                    R.id.rbNoPosgraduate -> {
                        setupStatus(
                            STATE.NO.state, linearLayoutList, textInputLayoutList,
                            autoCompleteTextViewList, isFabEnable = true
                        )
                    }
                    R.id.rbInProgressPosgraduate -> {
                        setupStatus(
                            STATE.IN_PROCESS.state,
                            textInputLayoutList = textInputLayoutList,
                            autoCompleteTextViewList = autoCompleteTextViewList,
                            isHidePosgraduates = true
                        )

                        val fields = arrayListOf(
                            tilEndYear1,
                            tilEndMonth1,
                            tilStartYear1,
                            tilStartMonth1,
                            tilTitleAchieved1,
                            tilType1,
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
                    }
                }
            }

            setData()

            fabReturn.setOnClickListener {
                listener?.onSelectStepView(1, R.id.academicFragment)
                sendDataOptionFragment(false)
            }
            fabNext.setOnClickListener {
                sendDataOptionFragment(true)
            }
        }
    }

    private fun setupStatus(
        state: Int = -1,
        linearLayoutList: List<LinearLayout>? = listOf(),
        textInputLayoutList: List<TextInputLayout>? = listOf(),
        autoCompleteTextViewList: List<AutoCompleteTextView>? = listOf(),
        isHidePosgraduates: Boolean = false,
        isFabEnable: Boolean = false
    ) {
        mState = state

        when (mState) {
            STATE.YES.state -> {
                isShowView(linearLayoutList, textInputLayoutList, true)
            }
            STATE.NO.state -> {
                isShowView(linearLayoutList, textInputLayoutList)
                if (!autoCompleteTextViewList.isNullOrEmpty()) {
                    autoCompleteTextViewList[0].setText("")
                }
            }
            STATE.IN_PROCESS.state -> {
                isShowView(textInputLayoutList = textInputLayoutList)
                if (!autoCompleteTextViewList.isNullOrEmpty()) {
                    autoCompleteTextViewList[0].setText("")
                }
            }
        }

        hidePosgraduates(isHidePosgraduates)
        fabNext.isEnabled = isFabEnable
    }

    private fun isShowView(
        linearLayoutList: List<LinearLayout>? = listOf(),
        textInputLayoutList: List<TextInputLayout>? = listOf(),
        isVisible: Boolean = false
    ) {
        if (!linearLayoutList.isNullOrEmpty()) {
            linearLayoutList[0].visibility = if (isVisible) View.VISIBLE else View.GONE
        }
        if (!textInputLayoutList.isNullOrEmpty()) {
            textInputLayoutList[0].visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        for (i in 1..4) {
            cleanAllAutocompleteTextView("atvType$i")
            cleanAllAutocompleteTextView("atvStartMonth$i")
            cleanAllAutocompleteTextView("atvEndMonth$i")
            cleanAllTextInputEditText("etTitleAchieved$i")
            cleanAllTextInputEditText("etStartYear$i")
            cleanAllTextInputEditText("etEndYear$i")
        }
    }

    private fun cleanAllAutocompleteTextView(resourceName: String) {
        val resourceID = resources.getIdentifier(
            resourceName, "id",
            requireActivity().packageName
        )
        if (resourceID != 0) {
            val atvTypes = mBinding.root.findViewById(resourceID) as AutoCompleteTextView?
            atvTypes?.setText("")
        }
    }

    private fun cleanAllTextInputEditText(resourceName: String) {
        val resourceID = resources.getIdentifier(
            resourceName, "id",
            requireActivity().packageName
        )
        if (resourceID != 0) {
            val tiet = mBinding.root.findViewById(resourceID) as TextInputEditText?
            tiet?.setText("")
        }
    }

    private fun addDataAllTextInputEditText(resourceName: String, dataPosgraduate: String) {
        val resourceID = resources.getIdentifier(
            resourceName, "id",
            requireActivity().packageName
        )
        if (resourceID != 0) {
            val tiet = mBinding.root.findViewById(resourceID) as TextInputEditText?
            tiet?.setText("")
            tiet?.setText(dataPosgraduate)
        }
    }

    private fun setterTextInputEditText(resourceName: String): String {
        val resourceID = resources.getIdentifier(
            resourceName, "id",
            requireActivity().packageName
        )
        if (resourceID != 0) {
            val tiet = mBinding.root.findViewById(resourceID) as TextInputEditText?
            return tiet?.text.toString().trim()
        }

        return ""
    }

    private fun addDataAllAutocompleteTextView(resourceName: String, dataPosgraduate: String) {
        val resourceID = resources.getIdentifier(
            resourceName, "id",
            requireActivity().packageName
        )
        if (resourceID != 0) {
            val atvTypes = mBinding.root.findViewById(resourceID) as AutoCompleteTextView?
            atvTypes?.setText(dataPosgraduate)
        }
    }

    private fun setterAutocompleteTextView(resourceName: String): String {
        val resourceID = resources.getIdentifier(
            resourceName, "id",
            requireActivity().packageName
        )
        if (resourceID != 0) {
            val atvTypes = mBinding.root.findViewById(resourceID) as AutoCompleteTextView?
            return atvTypes?.text.toString().trim()
        }

        return ""
    }

    private fun visibilityLinearLayout(resourceName: String, visibility: Int) {
        val resourceID = resources.getIdentifier(
            resourceName, "id",
            requireActivity().packageName
        )
        if (resourceID != 0) {
            val ll = mBinding.root.findViewById(resourceID) as LinearLayout?
            ll?.visibility = visibility
        }
    }

    private fun addSelectData() {
        mInfoUserViewModel.getNumberPosgraduate().observe(viewLifecycleOwner) { number ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, number)

            with(mBinding) {
                atvPosgraduate.setAdapter(adapter)
                atvPosgraduate.setOnItemClickListener { parent, _, position, id ->
                    mNumberPosgraduate = parent.getItemAtPosition(position).toString()

                    if (!mNumberPosgraduate.isNullOrEmpty() && mNumberPosgraduate.toInt() > 0) {
                        for (n in 1..mNumberPosgraduate.toInt()) {
                            when (n) {
                                1 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.GONE
                                    llPosgraduate3.visibility = View.GONE
                                    llPosgraduate4.visibility = View.GONE
                                }
                                2 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.VISIBLE
                                    llPosgraduate3.visibility = View.GONE
                                    llPosgraduate4.visibility = View.GONE
                                }
                                3 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.VISIBLE
                                    llPosgraduate3.visibility = View.VISIBLE
                                    llPosgraduate4.visibility = View.GONE
                                }
                                4 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.VISIBLE
                                    llPosgraduate3.visibility = View.VISIBLE
                                    llPosgraduate4.visibility = View.VISIBLE
                                }
                                else -> {
                                    hidePosgraduates()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setData() {
        with(mBinding) {
            mInfoUserViewModel.getTypePosgraduate().observe(viewLifecycleOwner) { types ->
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, types)

                val autoCompleteTextViewypeList = arrayListOf(
                    atvType1, atvType2, atvType3, atvType4
                )

                addAllTypes(autoCompleteTextViewypeList, adapter)
            }

            mInfoUserViewModel.getMonthList().observe(viewLifecycleOwner) { months ->
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, months)

                val autoCompleteTextViewMonthsList = arrayListOf(
                    atvStartMonth1, atvEndMonth1, atvStartMonth2, atvEndMonth2, atvStartMonth3,
                    atvEndMonth3, atvStartMonth4, atvEndMonth4
                )

                addAllMonths(autoCompleteTextViewMonthsList, adapter)
            }
        }
    }

    private fun hidePosgraduates(isFirstVisible: Boolean = false) {
        with(mBinding) {
            if (isFirstVisible) {
                llPosgraduates.visibility = View.VISIBLE
                llPosgraduate1.visibility = View.VISIBLE
            } else {
                llPosgraduate1.visibility = View.GONE
            }
            llPosgraduate2.visibility = View.GONE
            llPosgraduate3.visibility = View.GONE
            llPosgraduate4.visibility = View.GONE
        }
    }

    private fun addAllTypes(
        atvTypes: ArrayList<AutoCompleteTextView>, adapter: ArrayAdapter<String>
    ) {
        for (atvType in atvTypes) {
            with(atvType) {
                setAdapter(adapter)
                setOnItemClickListener { parent, _, position, id -> }
            }
        }
    }

    private fun sendDataOptionFragment(isSave: Boolean) {
        if (isSave) {
            when (mState) {
                STATE.YES.state -> {
                    with(mBinding) {
                        val total = atvPosgraduate.text.toString().toInt()
                        if (total > 0) {
                            when (total) {
                                1 -> {
                                    if (validateFields(
                                            tilEndYear1,
                                            tilEndMonth1,
                                            tilStartYear1,
                                            tilStartMonth1,
                                            tilTitleAchieved1,
                                            tilType1,
                                            fab = fabNext,
                                            context = requireContext()
                                        )
                                    ) {
                                        if (atvType1.text.toString().trim().isNotEmpty() &&
                                            etTitleAchieved1.text.toString().trim().isNotEmpty() &&
                                            atvStartMonth1.text.toString().trim().isNotEmpty() &&
                                            etStartYear1.text.toString().trim().isNotEmpty() &&
                                            atvEndMonth1.text.toString().trim().isNotEmpty() &&
                                            etEndYear1.text.toString().trim().isNotEmpty()
                                        ) {

                                            val posgraduate: ArrayList<Posgraduate> = ArrayList()
                                            posgraduate.add(
                                                Posgraduate(
                                                    type = atvType1.text.toString().trim(),
                                                    title = etTitleAchieved1.text.toString().trim(),
                                                    startMonth = atvStartMonth1.text.toString()
                                                        .trim(),
                                                    startYear = if (etStartYear1.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etStartYear1.text.toString().toInt(),
                                                    endMonth = atvEndMonth1.text.toString().trim(),
                                                    endYear = if (etEndYear1.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etEndYear1.text.toString().toInt()
                                                )
                                            )

                                            mInfoUserViewModel.setPosgraduateUser(
                                                PosgraduateUser(
                                                    state = STATE.IN_PROCESS,
                                                    total = posgraduate.size,
                                                    totalPosgraduate = posgraduate
                                                )
                                            )

                                            listener?.onSelectStepView(
                                                2,
                                                R.id.workExperienceFragment
                                            )
                                        }
                                    }

                                }
                                2 -> {
                                    if (validateFields(
                                            tilEndYear2,
                                            tilEndMonth2,
                                            tilStartYear2,
                                            tilStartMonth2,
                                            tilTitleAchieved2,
                                            tilType2,
                                            tilEndYear1,
                                            tilEndMonth1,
                                            tilStartYear1,
                                            tilStartMonth1,
                                            tilTitleAchieved1,
                                            tilType1,
                                            fab = fabNext,
                                            context = requireContext()
                                        )
                                    ) {
                                        if (atvType1.text.toString().trim().isNotEmpty() &&
                                            etTitleAchieved1.text.toString().trim().isNotEmpty() &&
                                            atvStartMonth1.text.toString().trim().isNotEmpty() &&
                                            etStartYear1.text.toString().trim().isNotEmpty() &&
                                            atvEndMonth1.text.toString().trim().isNotEmpty() &&
                                            etEndYear1.text.toString().trim().isNotEmpty() &&
                                            atvType2.text.toString().trim().isNotEmpty() &&
                                            etTitleAchieved2.text.toString().trim().isNotEmpty() &&
                                            atvStartMonth2.text.toString().trim().isNotEmpty() &&
                                            etStartYear2.text.toString().trim().isNotEmpty() &&
                                            atvEndMonth2.text.toString().trim().isNotEmpty() &&
                                            etEndYear2.text.toString().trim().isNotEmpty()
                                        ) {

                                            val posgraduate: ArrayList<Posgraduate> = ArrayList()
                                            posgraduate.add(
                                                Posgraduate(
                                                    type = atvType1.text.toString().trim(),
                                                    title = etTitleAchieved1.text.toString().trim(),
                                                    startMonth = atvStartMonth1.text.toString()
                                                        .trim(),
                                                    startYear = if (etStartYear1.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etStartYear1.text.toString().toInt(),
                                                    endMonth = atvEndMonth1.text.toString().trim(),
                                                    endYear = if (etEndYear1.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etEndYear1.text.toString().toInt()
                                                )
                                            )

                                            posgraduate.add(
                                                Posgraduate(
                                                    type = atvType2.text.toString().trim(),
                                                    title = etTitleAchieved2.text.toString().trim(),
                                                    startMonth = atvStartMonth2.text.toString()
                                                        .trim(),
                                                    startYear = if (etStartYear2.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etStartYear2.text.toString().toInt(),
                                                    endMonth = atvEndMonth2.text.toString().trim(),
                                                    endYear = if (etEndYear2.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etEndYear2.text.toString().toInt()
                                                )
                                            )

                                            mInfoUserViewModel.setPosgraduateUser(
                                                PosgraduateUser(
                                                    state = STATE.IN_PROCESS,
                                                    total = posgraduate.size,
                                                    totalPosgraduate = posgraduate
                                                )
                                            )

                                            listener?.onSelectStepView(
                                                2,
                                                R.id.workExperienceFragment
                                            )
                                        }
                                    }
                                }
                                3 -> {
                                    if (validateFields(
                                            tilEndYear3,
                                            tilEndMonth3,
                                            tilStartYear3,
                                            tilStartMonth3,
                                            tilTitleAchieved3,
                                            tilType3,
                                            tilEndYear2,
                                            tilEndMonth2,
                                            tilStartYear2,
                                            tilStartMonth2,
                                            tilTitleAchieved2,
                                            tilType2,
                                            tilEndYear1,
                                            tilEndMonth1,
                                            tilStartYear1,
                                            tilStartMonth1,
                                            tilTitleAchieved1,
                                            tilType1,
                                            fab = fabNext,
                                            context = requireContext()
                                        )
                                    ) {
                                        if (atvType1.text.toString().trim().isNotEmpty() &&
                                            etTitleAchieved1.text.toString().trim().isNotEmpty() &&
                                            atvStartMonth1.text.toString().trim().isNotEmpty() &&
                                            etStartYear1.text.toString().trim().isNotEmpty() &&
                                            atvEndMonth1.text.toString().trim().isNotEmpty() &&
                                            etEndYear1.text.toString().trim().isNotEmpty() &&
                                            atvType2.text.toString().trim().isNotEmpty() &&
                                            etTitleAchieved2.text.toString().trim().isNotEmpty() &&
                                            atvStartMonth2.text.toString().trim().isNotEmpty() &&
                                            etStartYear2.text.toString().trim().isNotEmpty() &&
                                            atvEndMonth2.text.toString().trim().isNotEmpty() &&
                                            etEndYear2.text.toString().trim().isNotEmpty() &&
                                            atvType3.text.toString().trim().isNotEmpty() &&
                                            etTitleAchieved3.text.toString().trim().isNotEmpty() &&
                                            atvStartMonth3.text.toString().trim().isNotEmpty() &&
                                            etStartYear3.text.toString().trim().isNotEmpty() &&
                                            atvEndMonth3.text.toString().trim().isNotEmpty() &&
                                            etEndYear3.text.toString().trim().isNotEmpty()
                                        ) {

                                            val posgraduate: ArrayList<Posgraduate> = ArrayList()
                                            posgraduate.add(
                                                Posgraduate(
                                                    type = atvType1.text.toString().trim(),
                                                    title = etTitleAchieved1.text.toString().trim(),
                                                    startMonth = atvStartMonth1.text.toString()
                                                        .trim(),
                                                    startYear = if (etStartYear1.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etStartYear1.text.toString().toInt(),
                                                    endMonth = atvEndMonth1.text.toString().trim(),
                                                    endYear = if (etEndYear1.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etEndYear1.text.toString().toInt()
                                                )
                                            )

                                            posgraduate.add(
                                                Posgraduate(
                                                    type = atvType2.text.toString().trim(),
                                                    title = etTitleAchieved2.text.toString().trim(),
                                                    startMonth = atvStartMonth2.text.toString()
                                                        .trim(),
                                                    startYear = if (etStartYear2.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etStartYear2.text.toString().toInt(),
                                                    endMonth = atvEndMonth2.text.toString().trim(),
                                                    endYear = if (etEndYear2.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etEndYear2.text.toString().toInt()
                                                )
                                            )

                                            posgraduate.add(
                                                Posgraduate(
                                                    type = atvType3.text.toString().trim(),
                                                    title = etTitleAchieved3.text.toString().trim(),
                                                    startMonth = atvStartMonth3.text.toString()
                                                        .trim(),
                                                    startYear = if (etStartYear3.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etStartYear3.text.toString().toInt(),
                                                    endMonth = atvEndMonth3.text.toString().trim(),
                                                    endYear = if (etEndYear3.text.toString()
                                                            .isEmpty()
                                                    ) 0
                                                    else etEndYear3.text.toString().toInt()
                                                )
                                            )

                                            mInfoUserViewModel.setPosgraduateUser(
                                                PosgraduateUser(
                                                    state = STATE.IN_PROCESS,
                                                    total = posgraduate.size,
                                                    totalPosgraduate = posgraduate
                                                )
                                            )

                                            listener?.onSelectStepView(
                                                2,
                                                R.id.workExperienceFragment
                                            )
                                        }
                                    }
                                }
                                4 -> {}
                            }
                        }
                    }
                }
                STATE.NO.state -> {
                    mInfoUserViewModel.setPosgraduateUser(
                        PosgraduateUser(
                            state = STATE.NO
                        )
                    )

                    listener?.onSelectStepView(2, R.id.workExperienceFragment)
                }
                STATE.IN_PROCESS.state -> {
                    with(mBinding) {
                        if (validateFields(
                                tilEndYear1,
                                tilEndMonth1,
                                tilStartYear1,
                                tilStartMonth1,
                                tilTitleAchieved1,
                                tilType1,
                                fab = fabNext,
                                context = requireContext()
                            )
                        ) {

                            if (atvType1.text.toString().trim().isNotEmpty() &&
                                etTitleAchieved1.text.toString().trim().isNotEmpty() &&
                                atvStartMonth1.text.toString().trim().isNotEmpty() &&
                                etStartYear1.text.toString().trim().isNotEmpty() &&
                                atvEndMonth1.text.toString().trim().isNotEmpty() &&
                                etEndYear1.text.toString().trim().isNotEmpty()
                            ) {

                                val posgraduate: ArrayList<Posgraduate> = ArrayList()
                                posgraduate.add(
                                    Posgraduate(
                                        type = atvType1.text.toString().trim(),
                                        title = etTitleAchieved1.text.toString().trim(),
                                        startMonth = atvStartMonth1.text.toString().trim(),
                                        startYear = if (etStartYear1.text.toString().isEmpty()) 0
                                        else etStartYear1.text.toString().toInt(),
                                        endMonth = atvEndMonth1.text.toString().trim(),
                                        endYear = if (etEndYear1.text.toString().isEmpty()) 0
                                        else etEndYear1.text.toString().toInt()
                                    )
                                )

                                mInfoUserViewModel.setPosgraduateUser(
                                    PosgraduateUser(
                                        state = STATE.IN_PROCESS,
                                        total = posgraduate.size,
                                        totalPosgraduate = posgraduate
                                    )
                                )

                                listener?.onSelectStepView(2, R.id.workExperienceFragment)
                            }

                        }

                    }
                }
            }

        } else {
            when (mState) {
                STATE.YES.state -> {
                    val totalPosgraduate: ArrayList<Posgraduate> = ArrayList()

                    val total = if (atvPosgraduate.text.toString()
                            .isEmpty()
                    ) 0 else atvPosgraduate.text.toString().toInt()

                    if (total > 0) {
                        for (et in 1..total) {
                            setterAutocompleteTextView("atvEndMonth$et")

                            totalPosgraduate.add(
                                Posgraduate(
                                    type = setterAutocompleteTextView("atvType$et"),
                                    title = setterTextInputEditText("etTitleAchieved$et"),
                                    startMonth = setterAutocompleteTextView("atvStartMonth$et"),
                                    startYear = if (setterTextInputEditText("etStartYear$et").isEmpty()) 0
                                    else setterTextInputEditText("etStartYear$et").toInt(),
                                    endMonth = setterAutocompleteTextView("atvEndMonth$et"),
                                    endYear = if (setterTextInputEditText("etEndYear$et").isEmpty()) 0
                                    else setterTextInputEditText("etEndYear$et").toInt(),
                                )
                            )
                        }
                    }

                    mInfoUserViewModel.setPosgraduateUser(
                        PosgraduateUser(
                            state = STATE.YES,
                            total = if (atvPosgraduate.text.toString().isEmpty()) 0
                            else atvPosgraduate.text.toString().toInt(),
                            totalPosgraduate = totalPosgraduate
                        )
                    )
                }
                STATE.NO.state -> {
                    mInfoUserViewModel.setPosgraduateUser(
                        PosgraduateUser(
                            state = STATE.NO
                        )
                    )
                }
                STATE.IN_PROCESS.state -> {
                    val posgraduate: ArrayList<Posgraduate> = ArrayList()
                    posgraduate.add(
                        Posgraduate(
                            type = atvType1.text.toString().trim(),
                            title = etTitleAchieved1.text.toString().trim(),
                            startMonth = atvStartMonth1.text.toString().trim(),
                            startYear = if (etStartYear1.text.toString().isEmpty()) 0
                            else etStartYear1.text.toString().toInt(),
                            endMonth = atvEndMonth1.text.toString().trim(),
                            endYear = if (etEndYear1.text.toString().isEmpty()) 0
                            else etEndYear1.text.toString().toInt()
                        )
                    )

                    mInfoUserViewModel.setPosgraduateUser(
                        PosgraduateUser(
                            state = STATE.IN_PROCESS,
                            total = posgraduate.size,
                            totalPosgraduate = posgraduate
                        )
                    )
                }
            }

        }
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    listener?.onSelectStepView(1, R.id.academicFragment)
                    sendDataOptionFragment(false)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}

// [\w\._]{5,30}\+?[\w]{0,10}@[\w\.\-]{3,}\.\w{2,5}$
// angel.luis3@yahoo.edu.com.mx

// \d{0}[12]\d{0}?[09]\d{0}[0-9]\d{0}[0-9]
// 1879
// 1998
// 2030
// 2010
// 1996
// 0265
// 2406
// 2302
// 3578987
// 6548