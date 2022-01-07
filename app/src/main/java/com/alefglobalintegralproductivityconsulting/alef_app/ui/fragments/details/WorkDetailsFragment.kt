package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentWorkDetailsBinding

class WorkDetailsFragment : Fragment(R.layout.fragment_work_details) {

    private lateinit var mBinding: FragmentWorkDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentWorkDetailsBinding.bind(view)
    }

}