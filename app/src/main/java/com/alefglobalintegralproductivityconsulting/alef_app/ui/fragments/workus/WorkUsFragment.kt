package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.workus

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentWorkUsBinding

class WorkUsFragment : Fragment(R.layout.fragment_work_us) {

    private lateinit var mBinding: FragmentWorkUsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentWorkUsBinding.bind(view)

        mBinding.btnWorkWithUs.setOnClickListener {
            Toast.makeText(requireContext(), "En desarrollo! :D", Toast.LENGTH_SHORT).show()
        }
    }

}