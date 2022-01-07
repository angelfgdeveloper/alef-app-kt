package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentEditProfileBinding
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentWorkDetailsBinding

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private lateinit var mBinding: FragmentEditProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentEditProfileBinding.bind(view)
    }

}