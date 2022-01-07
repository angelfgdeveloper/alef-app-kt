package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentProfileBinding.bind(view)

        methods()
    }

    private fun methods() {
        mBinding.btnLogout.setOnClickListener { v -> logout() }

        mBinding.ibEdit.setOnClickListener {
            Navigation.findNavController(
                mBinding.root
            ).navigate(R.id.action_navigation_profile_to_editProfileFragment)
        }
    }

    private fun logout() {
//        SharedPreferencesManager.removeDataValue(AppConstants.USER_TOKEN)
//        val intent = Intent(activity, SplashActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(intent)
//        requireActivity().finish()
    }

}