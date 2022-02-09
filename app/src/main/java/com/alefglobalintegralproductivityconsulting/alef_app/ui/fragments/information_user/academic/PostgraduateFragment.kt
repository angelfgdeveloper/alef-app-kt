package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.academic

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentPostgraduateBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.InfoUserViewModel
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.viewmodel.STATE

class PostgraduateFragment : Fragment(R.layout.fragment_postgraduate) {

    private lateinit var mBinding: FragmentPostgraduateBinding
    private val mInfoUserViewModel: InfoUserViewModel by activityViewModels()
    private var listener: StepViewListener? = null

    private var mNumberPosgraduate: String = ""
    private var mState: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is StepViewListener) listener = activity as StepViewListener?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPostgraduateBinding.bind(view)

        mInfoUserViewModel.getInfoUser().observe(viewLifecycleOwner) {
            Log.d("PostgraduateFragment", it.toString())
        }

        mInfoUserViewModel.getAcademicUser().observe(viewLifecycleOwner) {
            Log.d("PostgraduateFragment", it.toString())
        }

        onBackPress()
        setupTextFields()
        addSelectData()
    }

    private fun setupTextFields() {
        with(mBinding) {

            rgPosgraduate.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbYesPosgraduate -> {
                        mState = STATE.YES.state
                        tilPosgraduate.visibility = View.VISIBLE
                        fabNext.isEnabled = false
                    }
                    R.id.rbNoPosgraduate -> {
                        mState = STATE.NO.state
                        tilPosgraduate.visibility = View.GONE
                        fabNext.isEnabled = true
                    }
                    R.id.rbInProgressPosgraduate -> {
                        mState = STATE.IN_PROCESS.state
                        tilPosgraduate.visibility = View.GONE
                        fabNext.isEnabled = false
                    }
                }
            }

            fabReturn.setOnClickListener {
                listener?.onSelectStepView(1, R.id.academicFragment)
            }
            fabNext.setOnClickListener {
                Toast.makeText(requireContext(), "En desarrollo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addSelectData() {
        mInfoUserViewModel.getNumberPosgraduate().observe(viewLifecycleOwner) { number ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, number)

            with(mBinding) {
                atvPosgraduate.setAdapter(adapter)
                atvPosgraduate.setOnItemClickListener { parent, _, position, id ->
                    mNumberPosgraduate = parent.getItemAtPosition(position).toString()
                }
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}