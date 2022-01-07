package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentStatisticsBinding
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentWorkDetailsBinding

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private lateinit var mBinding: FragmentStatisticsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentStatisticsBinding.bind(view)
    }

}