package com.companyglobal.alef_app.ui.fragments.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.OnVacantClickListener
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.core.utils.OnCloseBackPress
import com.companyglobal.alef_app.data.model.Vacant
import com.companyglobal.alef_app.data.model.VacantInfoExtra
import com.companyglobal.alef_app.data.remote.home.RemoteHomeDataSource
import com.companyglobal.alef_app.databinding.FragmentFavoritesBinding
import com.companyglobal.alef_app.domain.home.HomeRepoImpl
import com.companyglobal.alef_app.presentation.home.HomeViewModel
import com.companyglobal.alef_app.presentation.home.HomeViewModelFactory
import com.companyglobal.alef_app.ui.fragments.home.adapter.VacantAdapter
import com.google.gson.Gson

class FavoritesFragment : Fragment(R.layout.fragment_favorites),
    VacantAdapter.OnVacantClickListener {

    private lateinit var mBinding: FragmentFavoritesBinding
    private lateinit var mAdapter: VacantAdapter

    private var mOnVacantListener: OnVacantClickListener? = null
    private var mOnCloseListener: OnCloseBackPress? = null

    private val mViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeRepoImpl(RemoteHomeDataSource())
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnVacantClickListener) mOnVacantListener =
            activity as OnVacantClickListener?
        if (activity is OnCloseBackPress) mOnCloseListener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentFavoritesBinding.bind(view)

        onBackPress()
        setupVacanciesFavorite()
    }

    private fun setupVacanciesFavorite() {
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

                    val vacantFavorite: ArrayList<Vacant> = ArrayList()
                    for (vacant in result.data) {
                        if (vacant.isFavorite) {
                            vacantFavorite.add(vacant)
                        }
                    }

                    mAdapter = VacantAdapter(vacantFavorite, this)
                    mBinding.rvFavorites.adapter = mAdapter
                }
            }
        }
    }

    override fun onVacantClick(vacant: Vacant, vacantInfoExtra: VacantInfoExtra?) {
        val gson = Gson()
        val jsonVacant = gson.toJson(vacant)
        val jsonVacantInfoExtra = gson.toJson(vacantInfoExtra)
        mOnVacantListener?.onVacantDetails(
            jsonVacant,
            jsonVacantInfoExtra,
            false,
            R.id.nav_favorites
        )
    }

    private fun onBackPress() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mOnCloseListener?.onCloseActivity(false)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
}