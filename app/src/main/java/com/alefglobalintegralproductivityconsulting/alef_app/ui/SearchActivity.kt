package com.alefglobalintegralproductivityconsulting.alef_app.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.AppConstants
import com.alefglobalintegralproductivityconsulting.alef_app.core.Result
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.VacantInfoExtra
import com.alefglobalintegralproductivityconsulting.alef_app.data.remote.home.RemoteHomeDataSource
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.ActivitySearchBinding
import com.alefglobalintegralproductivityconsulting.alef_app.domain.home.HomeRepoImpl
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.home.HomeViewModel
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.home.HomeViewModelFactory
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home.adapter.VacantAdapter
import com.google.gson.Gson

class SearchActivity : AppCompatActivity(), VacantAdapter.OnVacantClickListener {

    private lateinit var mBinding: ActivitySearchBinding
    private lateinit var mAdapter: VacantAdapter

    private val mViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(RemoteHomeDataSource())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setSupportActionBar(mBinding.toolbar)

        mBinding.ivBackPress.setOnClickListener { finish() }

        val search = intent.getStringExtra(AppConstants.SEARCH_GENERAL)
        val location = intent.getStringExtra(AppConstants.SEARCH_LOCATION)

        if (search!!.isNotEmpty() && location!!.isNotEmpty()) {
            mBinding.etSearch.setText("$search - $location")
        } else if (search.isNotEmpty()) {
            mBinding.etSearch.setText("$search")
        } else if (location!!.isNotEmpty()) {
            mBinding.etSearch.setText("$location")
        } else {
            mBinding.etSearch.setText("Todo")
        }

        setupVacancies()
    }

    private fun setupVacancies() {
        mViewModel.fetchVacancies().observe(this) { result ->
            when (result) {
                is Result.Failure -> {
                    mBinding.llLoading.visibility = View.GONE
                    Toast.makeText(
                        this,
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

    }
}