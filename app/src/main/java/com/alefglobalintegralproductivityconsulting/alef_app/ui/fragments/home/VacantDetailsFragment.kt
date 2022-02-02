package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.core.utils.Timestamp
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentVacantDetailsBinding
import com.google.gson.Gson

class VacantDetailsFragment : Fragment(R.layout.fragment_vacant_details) {

    private lateinit var mBinding: FragmentVacantDetailsBinding

    private var mVacant: Vacant? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentVacantDetailsBinding.bind(view)

        init()
        setupData()
    }

    private fun init() {
        val gson = Gson()
        val jsonVacant = arguments?.getString(AppConstants.DETAILS_VACANT)
        mVacant = gson.fromJson(jsonVacant, Vacant::class.java)
    }

    private fun setupData() {
        with(mBinding) {
            tvTitleVacantDetails.text = mVacant?.title
            tvCompany.text = mVacant?.company
            tvLocation.text = mVacant?.location

            val createdAt = (mVacant?.timestamp?.time?.div(1000L))?.let {
                Timestamp.getTimeAgo(it.toInt())
            }

            tvTimestamp.text = createdAt
            tvDescription.text = mVacant?.description

            if ((mVacant?.firstSalary != -1 || mVacant?.secondSalary != -1) && mVacant?.paymentMethod!!.isNotEmpty()) {
                llSalary.visibility = View.VISIBLE

                if (mVacant?.firstSalary != -1 && mVacant?.secondSalary != -1) {
                    tvSalary.text = "$${mVacant?.firstSalary} - $${mVacant?.secondSalary} MXN"
                } else if (mVacant?.firstSalary != -1) {
                    tvSalary.text = "$${mVacant?.firstSalary} MXN"
                } else if (mVacant?.secondSalary != -1) {
                    tvSalary.text = "$${mVacant?.secondSalary} MXN"
                }

                tvPayments.text = mVacant?.paymentMethod
            } else {
                llSalary.visibility = View.GONE
            }

            cbFavorite.isChecked = mVacant?.isFavorite!!
            cbFavorite.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
                }
            }

            btnApply.setOnClickListener {
                Toast.makeText(context, "Poatularme", Toast.LENGTH_SHORT).show()
            }

            ibShare.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, "Hey ve nuestra vacante: ${mVacant?.title}")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Compartir a:"))
            }
        }
    }

}