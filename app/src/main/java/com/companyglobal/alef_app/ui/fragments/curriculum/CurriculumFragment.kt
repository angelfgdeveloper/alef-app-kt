package com.companyglobal.alef_app.ui.fragments.curriculum

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.core.utils.OnCloseBackPress
import com.companyglobal.alef_app.data.model.info.ResponsePersonal
import com.companyglobal.alef_app.data.remote.info.RemoteInformationUser
import com.companyglobal.alef_app.databinding.FragmentCurriculumBinding
import com.companyglobal.alef_app.domain.info.InformationUserRepoImpl
import com.companyglobal.alef_app.presentation.info.InformationUserViewModel
import com.companyglobal.alef_app.presentation.info.InformationUserViewModelFactory
import com.companyglobal.alef_app.services.info.RetrofitClient
import kotlinx.android.synthetic.main.fragment_curriculum.*
import kotlinx.android.synthetic.main.fragment_curriculum.view.*
import kotlinx.coroutines.withContext

class CurriculumFragment : Fragment(R.layout.fragment_curriculum) {

    private lateinit var mBinding: FragmentCurriculumBinding
    private var mOnCloseListener: OnCloseBackPress? = null

    private val mViewModel by viewModels<InformationUserViewModel> {
        InformationUserViewModelFactory(
            InformationUserRepoImpl(
                RemoteInformationUser(RetrofitClient.webService)
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnCloseBackPress) mOnCloseListener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentCurriculumBinding.bind(view)

        onBackPress()

        setDataProfile()

        mBinding.chipIsVisible.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mBinding.chipIsVisible.text = "Perfil: Visible"
            } else {
                mBinding.chipIsVisible.text = "Perfil: Invisible"
            }
        }
    }

    private fun setDataProfile() {
        mViewModel.getPersonal().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("Get-Personal", result.toString())
                }
                is Result.Loading -> {
                    Log.d("Get-Personal", result.toString())
                }
                is Result.Success -> {
                    with(result.data) {
                        val fullName = "${this.informationUser?.name.toString()} ${this.informationUser?.lastName} ${this.informationUser?.motherLastName}"
                        val birthPlace = "${this.informationUser?.state}, ${this.informationUser?.town}"
                        val fullAddress = "${this.informationUser?.street}, ${this.informationUser?.suburb} C.P. ${this.informationUser?.postalCode}"

                        val curp = if (this.informationUser?.curp?.isNotEmpty()!!) this.informationUser.curp else "Sin datos"
                        val rfc = if (this.informationUser.rfc.isNotEmpty()) this.informationUser.rfc else "Sin datos"
                        val nss = if (this.informationUser.nss.isNotEmpty()) this.informationUser.nss else "Sin datos"

                        tvName.text = fullName
                        tvFullName.text = fullName
                        tvDate.text = informationUser.birth
                        tvBirthplace.text = birthPlace
                        tvFullAddress.text = fullAddress
                        tvCURP.text = curp
                        tvRFC.text = rfc
                        tvNSS.text = nss
                    }
                }
            }
        }

        mViewModel.getAcademic().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("Get-Academic", result.toString())
                }
                is Result.Loading -> {
                    Log.d("Get-Academic", result.toString())
                }
                is Result.Success -> {
                    with(result.data) {
                        tvInstitute.text = this.institute
                        tvTitleAchieved.text = "Sin especialidad"
                        tvEndDate.text = "${this.endMonth} de ${this.endYear}"
                    }
                }
            }
        }

        mViewModel.getWork().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Failure -> {
                    Log.d("Get-Work", result.toString())
                }
                is Result.Loading -> {
                    Log.d("Get-Work", result.toString())
                }
                is Result.Success -> {
                    with(result.data) {
                        tvCompany.text = this.works?.get(0)?.company.toString()
                        tvStartDateWork.text = "${this.works?.get(0)?.startMonth.toString()} de ${this.works?.get(0)?.startYear.toString()}"
                        tvEndDateWork.text = "${this.works?.get(0)?.endMonth.toString()} de ${this.works?.get(0)?.endYear.toString()}"
                    }
                }
            }
        }
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mOnCloseListener?.onCloseActivity(false)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
}