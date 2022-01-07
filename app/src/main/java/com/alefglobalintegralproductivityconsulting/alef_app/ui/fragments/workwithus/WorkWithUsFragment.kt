package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.workwithus

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentHomeBinding
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentWorkWithUsBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home.HomeViewModel

class WorkWithUsFragment : Fragment(R.layout.fragment_work_with_us) {

    private lateinit var mBinding: FragmentWorkWithUsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentWorkWithUsBinding.bind(view)

        mBinding.btnWorkWithUs.setOnClickListener { v ->
            Toast.makeText(
                activity,
                "Disculpe las molestias estamos en desarrollo :D",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}