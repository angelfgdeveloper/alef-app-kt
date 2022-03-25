package com.companyglobal.alef_app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.StepViewListener
import com.companyglobal.alef_app.databinding.ActivityInformationUserBinding

class InformationUserActivity : AppCompatActivity(), StepViewListener {

    private lateinit var mBinding: ActivityInformationUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityInformationUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.stepView.state
            .steps(object : ArrayList<String?>() {
                init {
                    add(getString(R.string.info_personal))
                    add(getString(R.string.info_academic))
                    add(getString(R.string.info_work_experience))
                }
            })
            .stepsNumber(resources.getInteger(R.integer.steps_number))
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