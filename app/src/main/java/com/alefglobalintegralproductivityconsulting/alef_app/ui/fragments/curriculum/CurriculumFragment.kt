package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.curriculum

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentCurriculumBinding

class CurriculumFragment : Fragment(R.layout.fragment_curriculum) {

    private lateinit var mBinding: FragmentCurriculumBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentCurriculumBinding.bind(view)

        mBinding.chipIsVisible.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
//                mBinding.chipIsVisible.checkedIcon =
//                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_success)
//                mBinding.chipIsVisible.checkedIconTint =
//                    AppCompatResources.getColorStateList(requireContext(), R.color.purple_500)
                mBinding.chipIsVisible.text = "Perfil: Visible"
            } else {
//                mBinding.chipIsVisible.checkedIcon =
//                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_state_circle)
                mBinding.chipIsVisible.text = "Perfil: Invisible"
            }
        }
    }

}