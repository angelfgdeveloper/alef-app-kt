package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentVacantDetailsBinding
import com.google.gson.Gson

class VacantDetailsFragment : Fragment(R.layout.fragment_vacant_details) {

    private lateinit var mBinding: FragmentVacantDetailsBinding

    private var mVacant: Vacant? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentVacantDetailsBinding.bind(view)

        init()
        setupData()
    }

    private fun init() {
        val gson = Gson()
        val jsonVacant = arguments?.getString(AppConstants.DETAILS_VACANT)
        mVacant = gson.fromJson(jsonVacant, Vacant::class.java)
    }

    private fun setupData() {
        with(mBinding) {
            tvDetalles.text = mVacant?.let { vacant ->
                vacant.title
            }
        }
    }

}