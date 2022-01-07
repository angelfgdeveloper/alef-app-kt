package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.exit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentExitBinding

class ExitFragment : Fragment(R.layout.fragment_exit) {

    private lateinit var mBinding: FragmentExitBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentExitBinding.bind(view)
    }

}