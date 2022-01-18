package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.StepViewListener
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivityInformationUserBinding
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.academic.AcademicFragment
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.information_user.personal.PersonalFragment

class InformationUserActivity : AppCompatActivity(), StepViewListener {

    private lateinit var mBinding: ActivityInformationUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityInformationUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.stepView.state
            .steps(object : ArrayList<String?>() {
                init {
                    add("Información Personal")
                    add("Información Academica")
                    add("Información Laboral")
                    add("Referencias")
                }
            })
            .stepsNumber(4)
            .commit()

//        mBinding.stepView.setOnStepClickListener { step ->
//            when (step) {
//                0 -> Navigation.findNavController(this, R.id.nav_host_fragment)
//                    .navigate(R.id.personalFragment)
//                1 -> Navigation.findNavController(this, R.id.nav_host_fragment)
//                    .navigate(R.id.academicFragment)
//                else -> Toast.makeText(this, "Error en navegación", Toast.LENGTH_SHORT).show()
//            }
//
//            mBinding.stepView.go(step, true)
//        }

    }

    override fun onSelectStepView(step: Int, id: Int) {
        mBinding.stepView.go(step, true)
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(id)
    }

}