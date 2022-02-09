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
import kotlinx.android.synthetic.main.fragment_postgraduate.*


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
                        llPosgraduates.visibility = View.VISIBLE
                        hidePosgraduates()
                        fabNext.isEnabled = false
                    }
                    R.id.rbNoPosgraduate -> {
                        mState = STATE.NO.state
                        tilPosgraduate.visibility = View.GONE
                        llPosgraduates.visibility = View.GONE
                        fabNext.isEnabled = true
                        atvPosgraduate.setText("")
                        hidePosgraduates()
                    }
                    R.id.rbInProgressPosgraduate -> {
                        mState = STATE.IN_PROCESS.state
                        tilPosgraduate.visibility = View.GONE
                        fabNext.isEnabled = false
                        atvPosgraduate.setText("")
                        hidePosgraduates(true)
                    }
                }
            }

            setData()

            fabReturn.setOnClickListener {
                listener?.onSelectStepView(1, R.id.academicFragment)
            }
            fabNext.setOnClickListener {
                listener?.onSelectStepView(2, R.id.workExperienceFragment)
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

                    if (!mNumberPosgraduate.isNullOrEmpty() && mNumberPosgraduate.toInt() > 0) {
                        for (n in 1..mNumberPosgraduate.toInt()) {
                            when (n) {
                                1 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.GONE
                                    llPosgraduate3.visibility = View.GONE
                                    llPosgraduate4.visibility = View.GONE
                                }
                                2 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.VISIBLE
                                    llPosgraduate3.visibility = View.GONE
                                    llPosgraduate4.visibility = View.GONE
                                }
                                3 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.VISIBLE
                                    llPosgraduate3.visibility = View.VISIBLE
                                    llPosgraduate4.visibility = View.GONE
                                }
                                4 -> {
                                    llPosgraduate1.visibility = View.VISIBLE
                                    llPosgraduate2.visibility = View.VISIBLE
                                    llPosgraduate3.visibility = View.VISIBLE
                                    llPosgraduate4.visibility = View.VISIBLE
                                }
                                else -> {
                                    hidePosgraduates()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setData() {
        mInfoUserViewModel.getTypePosgraduate().observe(viewLifecycleOwner) { types ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, types)

            with(mBinding) {
                atvType1.setAdapter(adapter)
                atvType1.setOnItemClickListener { parent, _, position, id ->

                }

                atvType2.setAdapter(adapter)
                atvType2.setOnItemClickListener { parent, _, position, id ->

                }

                atvType3.setAdapter(adapter)
                atvType3.setOnItemClickListener { parent, _, position, id ->

                }

                atvType4.setAdapter(adapter)
                atvType4.setOnItemClickListener { parent, _, position, id ->

                }
            }
        }

        mInfoUserViewModel.getMonthList().observe(viewLifecycleOwner) { months ->
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, months)

            // 1
            atvStartMonth1.setAdapter(adapter)
            atvStartMonth1.setOnItemClickListener { parent, _, position, id ->

            }

            atvEndMonth1.setAdapter(adapter)
            atvEndMonth1.setOnItemClickListener { parent, _, position, id ->

            }

            // 2
            atvStartMonth2.setAdapter(adapter)
            atvStartMonth2.setOnItemClickListener { parent, _, position, id ->

            }

            atvEndMonth2.setAdapter(adapter)
            atvEndMonth2.setOnItemClickListener { parent, _, position, id ->

            }

            // 3
            atvStartMonth3.setAdapter(adapter)
            atvStartMonth3.setOnItemClickListener { parent, _, position, id ->

            }

            atvEndMonth3.setAdapter(adapter)
            atvEndMonth3.setOnItemClickListener { parent, _, position, id ->

            }

            // 4
            atvStartMonth4.setAdapter(adapter)
            atvStartMonth4.setOnItemClickListener { parent, _, position, id ->

            }

            atvEndMonth4.setAdapter(adapter)
            atvEndMonth4.setOnItemClickListener { parent, _, position, id ->

            }

        }
    }

    private fun hidePosgraduates(isFirstVisible: Boolean = false) {
        with(mBinding) {
            if (isFirstVisible) {
                llPosgraduates.visibility = View.VISIBLE
                llPosgraduate1.visibility = View.VISIBLE
            } else {
                llPosgraduate1.visibility = View.GONE
            }
            llPosgraduate2.visibility = View.GONE
            llPosgraduate3.visibility = View.GONE
            llPosgraduate4.visibility = View.GONE
        }
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    listener?.onSelectStepView(1, R.id.academicFragment)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}