package com.companyglobal.alef_app.ui.fragments.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.core.utils.OnCloseBackPress
import com.companyglobal.alef_app.core.utils.Timestamp
import com.companyglobal.alef_app.data.model.Vacant
import com.companyglobal.alef_app.data.model.postulation.RequestPostulation
import com.companyglobal.alef_app.data.model.postulation.ResponsePostulationStatus
import com.companyglobal.alef_app.data.remote.postulation.RemotePostulationDataSource
import com.companyglobal.alef_app.databinding.FragmentVacantDetailsBinding
import com.companyglobal.alef_app.domain.postulation.PostulationRepoImpl
import com.companyglobal.alef_app.presentation.postulation.PostulationViewModel
import com.companyglobal.alef_app.presentation.postulation.PostulationViewModelFactory
import com.companyglobal.alef_app.services.routes.RetrofitClientAPI
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_availability.view.*
import kotlinx.android.synthetic.main.item_workday.view.*

class VacantDetailsFragment : Fragment(R.layout.fragment_vacant_details) {

    private lateinit var mBinding: FragmentVacantDetailsBinding
    private var listener: OnCloseBackPress? = null

    private var mVacant: Vacant? = null

    //    private var mVacantInfoExtra: VacantInfoExtra? = null
    private var mIsActivity: Boolean? = null
    private var mIdFragment: Int? = null

    private val mViewModel by viewModels<PostulationViewModel> {
        PostulationViewModelFactory(
            PostulationRepoImpl(
                RemotePostulationDataSource(
                    RetrofitClientAPI.webServiceAPI
                )
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnCloseBackPress) listener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentVacantDetailsBinding.bind(view)

        setupActionBar()
        onBackPress()
        init()
        setupData()
    }

    private fun init() {
        val gson = Gson()
        val jsonVacant = arguments?.getString(AppConstants.DETAILS_VACANT)
        val jsonVacantInfoExtra = arguments?.getString(AppConstants.VACANT_INFO_EXTRA)
        mIsActivity = arguments?.getBoolean(AppConstants.IS_ACTIVITY)
        mIdFragment = arguments?.getInt(AppConstants.ID_NAV_FRAGMENT)

        mVacant = gson.fromJson(jsonVacant, Vacant::class.java)
//        mVacantInfoExtra = gson.fromJson(jsonVacantInfoExtra, VacantInfoExtra::class.java)

        val idVacant = mVacant?.id

        if (idVacant != null) {
            mViewModel.getPostulation(idVacant)
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Failure -> {
                            Log.d("Postulation - get", result.toString())
                        }
                        is Result.Loading -> {
                            Log.d("Postulation - get", result.toString())
                        }
                        is Result.Success -> {

                            val hasPostulation =
                                ResponsePostulationStatus(result.data.body()!!.status)

                            if (!hasPostulation.status) {
                                Toast.makeText(
                                    requireContext(),
                                    "Ya estás postulado a la vacante",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            mBinding.btnApply.isEnabled = hasPostulation.status
                        }
                    }
                }

        }
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(mBinding.toolbar)

        mBinding.toolbar.setNavigationOnClickListener {
            if (mIsActivity == true) {
                listener?.onCloseActivity(true)
            } else {
                listener?.onCloseActivity(false, mIdFragment)
            }
        }
    }

    private fun setupData() {
        with(mBinding) {
            tvTitleVacantDetails.text = mVacant?.title
            tvCompany.text = mVacant?.company?.name
            tvLocation.text = "${mVacant!!.location[0].state} ${mVacant!!.location[0].town}"

            tvMode.text = mVacant?.mode

            if (mVacant?.companyPaid!!) {
                ivStateCompany.visibility = View.VISIBLE
            } else {
                ivStateCompany.visibility = View.GONE
            }

            if (!mVacant?.workDay.isNullOrEmpty()) {
                val workDayMap = mVacant?.workDay

                if (workDayMap != null) {
                    for ((day, hour) in workDayMap) {
                        val workDays: View = layoutInflater.inflate(
                            R.layout.item_workday,
                            llWorkDay,
                            false
                        )

                        workDays.tvDay.text = day
                        workDays.tvHour.text = hour

                        llWorkDay.addView(workDays)
                    }
                }
            }

            if (!mVacant?.availability.isNullOrEmpty()) {
                llCompanyInfoExtra.visibility = View.VISIBLE
                tvTitleAvailability.visibility = View.VISIBLE
                vAvailability.visibility = View.VISIBLE

                val availabilityMap = mVacant?.availability
                if (availabilityMap != null) {
                    for ((title, checked) in availabilityMap) {
                        val radioButtons: View = layoutInflater.inflate(
                            R.layout.item_availability,
                            llCompanyInfoExtra,
                            false
                        )

                        radioButtons.tvAvailability.text = title

                        if (checked) {
                            radioButtons.rbYes.isChecked = true
                            radioButtons.rbNo.isChecked = false
                        } else {
                            radioButtons.rbYes.isChecked = false
                            radioButtons.rbNo.isChecked = true
                        }

                        llCompanyInfoExtra.addView(radioButtons)
                    }
                }

            } else {
                llCompanyInfoExtra.visibility = View.GONE
                tvTitleAvailability.visibility = View.GONE
                vAvailability.visibility = View.GONE
            }

            val createdAt = (mVacant?.created?.time?.div(1000L))?.let {
                Timestamp.getTimeAgo(it.toInt())
            }

            tvTimestamp.text = createdAt
            tvDescription.text = mVacant?.description

            if ((mVacant?.firstSalary != -1 || mVacant?.secondSalary != -1) && mVacant?.paymentMethod!!.isNotEmpty()) {
                llSalary.visibility = View.VISIBLE

                if (mVacant?.firstSalary != -1 && mVacant?.secondSalary != -1) {
                    tvSalary.text =
                        "$${mVacant?.firstSalary} - $${mVacant?.secondSalary} ${mVacant?.typeCurrency}"
                } else if (mVacant?.firstSalary != -1) {
                    tvSalary.text = "$${mVacant?.firstSalary} ${mVacant?.typeCurrency}"
                } else if (mVacant?.secondSalary != -1) {
                    tvSalary.text = "$${mVacant?.secondSalary} ${mVacant?.typeCurrency}"
                }

                tvPayments.text = mVacant?.paymentMethod
            } else {
                llSalary.visibility = View.GONE
            }

            cbFavorite.isChecked = mVacant?.isFavorite!!
            cbFavorite.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
                }
            }

            btnApply.setOnClickListener { view ->
                val idVacant = mVacant?.id
                val requestPostulation = mVacant?.company?.id?.let { RequestPostulation(it) }

                if (requestPostulation != null) {
                    if (idVacant != null) {
                        mViewModel.createPostulation(idVacant, requestPostulation)
                            .observe(viewLifecycleOwner) { result ->
                                when (result) {
                                    is Result.Failure -> {
                                        Log.d("Postulation - create", result.toString())
                                    }
                                    is Result.Loading -> {
                                        Log.d("Postulation - create", result.toString())
                                    }
                                    is Result.Success -> {
                                        val message = result.data.body()?.message.toString()

                                        Toast.makeText(
                                            requireContext(),
                                            if (!message.isNullOrEmpty()) message else "El usuario ya esta postulado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        btnApply.isEnabled = false
                                    }
                                }
                            }
                    }
                }

            }

            ibShare.setOnClickListener {
                shareVacant()
            }
        }
    }

    private fun shareVacant() {
        val url = "https://www.google.com.mx/"
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Hey ve una vacante que te puede interesar: ${mVacant?.title} - Visitanos en $url"
        )
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Compartir por:"))
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (mIsActivity == true) {
                        listener?.onCloseActivity(true)
                    } else {
                        listener?.onCloseActivity(false, mIdFragment)
                    }
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

}