package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.academic

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentPostgraduateBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel


class PostgraduateFragment : Fragment(R.layout.fragment_postgraduate) {

    private lateinit var mBinding: FragmentPostgraduateBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()
    private var listener: StepViewListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPostgraduateBinding.bind(view)

        mInfoUserViewModel.getInfoUser().observe(viewLifecycleOwner, {
            Log.d("PostgraduateFragment", it.toString())
        })

        mInfoUserViewModel.getAcademicUser().observe(viewLifecycleOwner, {
            Log.d("PostgraduateFragment", it.toString())
        })

        onBackPress()
        setupTextFields()
    }

    private fun setupTextFields() {
        with(mBinding) {

            fabReturn.setOnClickListener {
                listener?.onSelectStepView(1, R.id.academicFragment)
            }
            fabNext.setOnClickListener {
                Toast.makeText(requireContext(), "En desarrollo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    listener?.onSelectStepView(1, R.id.academicFragment)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}