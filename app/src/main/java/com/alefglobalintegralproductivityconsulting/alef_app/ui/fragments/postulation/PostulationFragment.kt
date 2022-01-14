package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.postulation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentPostulationBinding

class PostulationFragment : Fragment(R.layout.fragment_postulation) {

    private lateinit var mPostulationViewModel: PostulationViewModel
    private lateinit var mBinding: FragmentPostulationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPostulationViewModel = ViewModelProvider(this)[PostulationViewModel::class.java]
        mBinding = FragmentPostulationBinding.bind(view)

        mPostulationViewModel.titlePostulation.observe(viewLifecycleOwner, { title ->
            mBinding.tvPostulation.text = title
        })
    }

}