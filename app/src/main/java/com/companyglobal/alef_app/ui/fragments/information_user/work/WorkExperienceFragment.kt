package com.companyglobal.alef_app.ui.fragments.information_user.work

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.core.StepViewListener
import com.companyglobal.alef_app.core.utils.SharedPreferencesManager
import com.companyglobal.alef_app.core.utils.Validators.Companion.addAllMonths
import com.companyglobal.alef_app.core.utils.Validators.Companion.validateFields
import com.companyglobal.alef_app.data.model.info.AllWorks
import com.companyglobal.alef_app.data.model.info.RequestWork
import com.companyglobal.alef_app.data.remote.info.RemoteInformationUser
import com.companyglobal.alef_app.databinding.FragmentWorkExperienceBinding
import com.companyglobal.alef_app.domain.info.InformationUserRepoImpl
import com.companyglobal.alef_app.presentation.info.InformationUserViewModel
import com.companyglobal.alef_app.presentation.info.InformationUserViewModelFactory
import com.companyglobal.alef_app.services.info.RetrofitClient
import com.companyglobal.alef_app.ui.HomeActivity
import com.companyglobal.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel
import com.companyglobal.alef_app.ui.fragments.information_user.viewmodel.STATE
import com.google.android.material.textfield.TextInputEditText

class WorkExperienceFragment : Fragment(R.layout.fragment_work_experience) {

    private lateinit var mBinding: FragmentWorkExperienceBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()
    private var listener: StepViewListener? = null

    private var mIsStepView = false
    private var mIsActive = false

    private val mViewModel by viewModels<InformationUserViewModel> {
        InformationUserViewModelFactory(
            InformationUserRepoImpl(
                RemoteInformationUser(RetrofitClient.webService)
            )
        )
    }

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

        mInfoUserViewModel.getWork().observe(viewLifecycleOwner) { work ->
            Log.d("TAG", work.toString())
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

    private fun setupTextFields() {
        with(mBinding) {

            rgWorkExperience.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbYesWorkExperience -> {
                        fabNext.visibility = View.VISIBLE
                        fabNext.isEnabled = false
                        llWorkExperiences.visibility = View.VISIBLE
                        mIsActive = true
                    }
                    R.id.rbNoWorkExperience -> {
                        fabNext.visibility = View.VISIBLE
                        fabNext.isEnabled = true
                        llWorkExperiences.visibility = View.GONE
                        mIsActive = false
                    }
                }
            }

            val fields = arrayListOf(
                tilCompany1, tilJob1, tilArea1, tilStartMonth1, tilStartYear1, tilEndMonth1,
                tilEndYear1, tilDescription1
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

            fabNext.setOnClickListener { goToHome(mIsActive) }
        }
    }

    private fun goToHome(isActive: Boolean) {
        if (isActive) {
            with(mBinding) {
                if (validateFields(
                        tilDescription1,
                        tilEndYear1,
                        tilEndMonth1,
                        tilStartYear1,
                        tilStartMonth1,
                        tilArea1,
                        tilJob1,
                        tilCompany1,
                        fab = fabNext,
                        context = requireContext()
                    )
                ) {
                    val works: ArrayList<AllWorks> = ArrayList()

                    works.add(
                        AllWorks(
                            company = etCompany1.text.toString(),
                            job = etJob1.text.toString(),
                            area = etArea1.text.toString(),
                            startMonth = atvStartMonth1.text.toString().trim(),
                            startYear = if (etStartYear1.text.toString().isEmpty()) 0
                            else etStartYear1.text.toString().toInt(),
                            endMonth = atvEndMonth1.text.toString().trim(),
                            endYear = if (etEndYear1.text.toString().isEmpty()) 0
                            else etEndYear1.text.toString().toInt(),
                            description = etDescription1.text.toString()
                        )
                    )

                    if (etCompany2.text.toString().isNotEmpty() &&
                        etJob2.text.toString().isNotEmpty() &&
                        etArea2.text.toString().isNotEmpty() &&
                        atvStartMonth2.text.toString().isNotEmpty() &&
                        etStartYear2.text.toString().isNotEmpty() &&
                        atvEndMonth2.text.toString().isNotEmpty() &&
                        etEndYear2.text.toString().isNotEmpty() &&
                        etDescription2.text.toString().isNotEmpty()
                    ) {
                        works.add(
                            AllWorks(
                                company = etCompany2.text.toString(),
                                job = etJob2.text.toString(),
                                area = etArea2.text.toString(),
                                startMonth = atvStartMonth2.text.toString().trim(),
                                startYear = if (etStartYear2.text.toString().isEmpty()) 0
                                else etStartYear2.text.toString().toInt(),
                                endMonth = atvEndMonth2.text.toString().trim(),
                                endYear = if (etEndYear2.text.toString().isEmpty()) 0
                                else etEndYear2.text.toString().toInt(),
                                description = etDescription2.text.toString()
                            )
                        )

                    }

                    if (etCompany3.text.toString().isNotEmpty() &&
                        etJob3.text.toString().isNotEmpty() &&
                        etArea3.text.toString().isNotEmpty() &&
                        atvStartMonth3.text.toString().isNotEmpty() &&
                        etStartYear3.text.toString().isNotEmpty() &&
                        atvEndMonth3.text.toString().isNotEmpty() &&
                        etEndYear3.text.toString().isNotEmpty() &&
                        etDescription3.text.toString().isNotEmpty()
                    ) {
                        works.add(
                            AllWorks(
                                company = etCompany3.text.toString(),
                                job = etJob3.text.toString(),
                                area = etArea3.text.toString(),
                                startMonth = atvStartMonth3.text.toString().trim(),
                                startYear = if (etStartYear3.text.toString().isEmpty()) 0
                                else etStartYear3.text.toString().toInt(),
                                endMonth = atvEndMonth3.text.toString().trim(),
                                endYear = if (etEndYear3.text.toString().isEmpty()) 0
                                else etEndYear3.text.toString().toInt(),
                                description = etDescription3.text.toString()
                            )
                        )
                    }

                    val requestWork = RequestWork(state = STATE.YES.state, works = works)

                    mViewModel.setWork(requestWork).observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is Result.Failure -> {
                                Log.d("WorkExperience", "Failure.. $result")
                            }
                            is Result.Loading -> {
                                Log.d("WorkExperience", "Loading.. $result")
                            }
                            is Result.Success -> {
                                Log.d("WorkExperience", result.data.body().toString())

                                SharedPreferencesManager.removeAllData(AppConstants.USER_ID_GOOGLE)
                                val intent = Intent(requireContext(), HomeActivity::class.java)
                                intent.putExtra(AppConstants.IS_LOGIN_USER, true)
                                startActivity(intent)
                                activity?.finishAffinity()
                            }
                        }
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Solicitud denegada, requiere llenar todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {

            val requestWork = RequestWork(state = STATE.NO.state)

            mViewModel.setWork(requestWork).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Failure -> {
                        Log.d("WorkExperience", "Failure.. $result")
                    }
                    is Result.Loading -> {
                        Log.d("WorkExperience", "Loading.. $result")
                    }
                    is Result.Success -> {
                        Log.d("WorkExperience", result.data.body().toString())

                        SharedPreferencesManager.removeAllData(AppConstants.USER_ID_GOOGLE)
                        val intent = Intent(requireContext(), HomeActivity::class.java)
                        intent.putExtra(AppConstants.IS_LOGIN_USER, true)
                        startActivity(intent)
                        activity?.finishAffinity()
                    }
                }
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