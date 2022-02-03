package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.core.Result
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.Vacant
import com.alefglobalintegralproductivityconsulting.alef_app.data.model.VacantInfoExtra
import com.alefglobalintegralproductivityconsulting.alef_app.data.remote.home.RemoteHomeDataSource
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentFavoritesBinding
import com.alefglobalintegralproductivityconsulting.alef_app.domain.home.HomeRepoImpl
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.home.HomeViewModel
import com.alefglobalintegralproductivityconsulting.alef_app.presentation.home.HomeViewModelFactory
import com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.home.adapter.VacantAdapter

class FavoritesFragment : Fragment(R.layout.fragment_favorites), VacantAdapter.OnVacantClickListener {

    private lateinit var mBinding: FragmentFavoritesBinding
    private lateinit var mAdapter: VacantAdapter

    private val mViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(RemoteHomeDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentFavoritesBinding.bind(view)

        setupVacanciesFavorite()
    }

    private fun setupVacanciesFavorite() {
        mViewModel.fetchVacancies().observe(viewLifecycleOwner, { result ->
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

                    val vacantFavorite: ArrayList<Vacant> = ArrayList()
                    for(vacant in result.data) {
                        if (vacant.isFavorite) {
                            vacantFavorite.add(vacant)
                        }
                    }

                    mAdapter = VacantAdapter(vacantFavorite, this)
                    mBinding.rvFavorites.adapter = mAdapter
                }
            }
        })
    }

    override fun onVacantClick(vacant: Vacant, vacantInfoExtra: VacantInfoExtra?) {

    }
}