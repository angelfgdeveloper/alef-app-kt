package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.curriculum

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentCurriculumBinding

class CurriculumFragment : Fragment(R.layout.fragment_curriculum) {

    private lateinit var mBinding: FragmentCurriculumBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentCurriculumBinding.bind(view)
    }

}