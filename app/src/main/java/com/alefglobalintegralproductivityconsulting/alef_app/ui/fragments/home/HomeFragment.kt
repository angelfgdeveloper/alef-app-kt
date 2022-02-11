package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.core.Result
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.VacantInfoExtra
import com.alefglobalintegralproductivityconsulting.alef_app.data.remote.home.RemoteHomeDataSource
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentHomeBinding
import com.alefglobalintegralproductivityconsulting.alef_app.domain.home.HomeRepoImpl
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.home.HomeViewModel
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.home.HomeViewModelFactory
import com.alefglobalintegralproductivityconsulting.alef_app.ui.SearchActivity
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home.adapter.VacantAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), VacantAdapter.OnVacantClickListener {

    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mAdapter: VacantAdapter

    private var isLogin: Boolean = false

    private val mViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(RemoteHomeDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)

        setupArguments()
        setupVacancies()
        setupTextField()
    }

    private fun setupTextField() {
        with(mBinding) {
            btnSearch.setOnClickListener {
                val search = etSearch.text.toString().trim()
                val location = etLocation.text.toString().trim()

                val i = Intent(requireContext(), SearchActivity::class.java)
                i.putExtra(AppConstants.SEARCH_GENERAL, search)
                i.putExtra(AppConstants.SEARCH_LOCATION, location)
                startActivity(i)
            }
        }
    }

    private fun setupArguments() {
        arguments?.getBoolean(AppConstants.IS_LOGIN_USER)?.let {
            isLogin = it
            if (isLogin) {
                llTestCompany.visibility = View.VISIBLE
            } else {
                llTestCompany.visibility = View.GONE
            }
        }
    }

    private fun setupVacancies() {
        mViewModel.fetchVacancies().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Failure -> {
                    mBinding.llLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Result.Loading -> {
                    mBinding.llLoading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    mBinding.llLoading.visibility = View.GONE

                    if (result.data.isEmpty()) {
                        mBinding.llDisconnected.visibility = View.VISIBLE
                        mBinding.llConnected.visibility = View.GONE
                        return@observe
                    } else {
                        mBinding.llDisconnected.visibility = View.GONE
                        mBinding.llConnected.visibility = View.VISIBLE
                    }

                    mAdapter = VacantAdapter(result.data, this)
                    mBinding.rvVacancies.adapter = mAdapter
                }
            }
        }
    }

    override fun onVacantClick(vacant: Vacant, vacantInfoExtra: VacantInfoExtra?) {
        val gson = Gson()
        val jsonVacant = gson.toJson(vacant)
        val jsonVacantInfoExtra = gson.toJson(vacantInfoExtra)

        val bundle = bundleOf(
            AppConstants.DETAILS_VACANT to jsonVacant,
            AppConstants.VACANT_INFO_EXTRA to jsonVacantInfoExtra
        )
        findNavController().navigate(R.id.action_nav_home_to_vacantDetailsFragment, bundle)
    }
}