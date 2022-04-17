package com.companyglobal.alef_app.ui.fragments.postulation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.companyglobal.alef_app.R
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.core.utils.OnCloseBackPress
import com.companyglobal.alef_app.data.model.Postulation
import com.companyglobal.alef_app.data.remote.postulation.RemotePostulationDataSource
import com.companyglobal.alef_app.databinding.FragmentPostulationBinding
import com.companyglobal.alef_app.domain.postulation.PostulationRepoImpl
import com.companyglobal.alef_app.presentation.postulation.PostulationViewModel
import com.companyglobal.alef_app.presentation.postulation.PostulationViewModelFactory
import com.companyglobal.alef_app.services.routes.RetrofitClientAPI
import com.companyglobal.alef_app.ui.fragments.postulation.adapter.PostulationAdapter

class PostulationFragment : Fragment(R.layout.fragment_postulation),
    PostulationAdapter.OnPostulationClickListener {

    private lateinit var mBinding: FragmentPostulationBinding
    private lateinit var mAdapter: PostulationAdapter
    private var mOnCloseListener: OnCloseBackPress? = null

    private val mViewModel by viewModels<PostulationViewModel> {
        PostulationViewModelFactory(
            PostulationRepoImpl(RemotePostulationDataSource(RetrofitClientAPI.webServiceAPI))
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is OnCloseBackPress) mOnCloseListener = activity as OnCloseBackPress?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentPostulationBinding.bind(view)

        onBackPress()
        setupPostulations()
    }

    private fun setupPostulations() {
        mViewModel.fetchPostulations().observe(viewLifecycleOwner) { result ->
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

                    mAdapter = PostulationAdapter(result.data, this)
                    mBinding.rvPostulation.adapter = mAdapter
                }
            }

        }
    }

    override fun onPostulationClick(postulation: Postulation) {
        Log.d("PostulationFragment", postulation.toString())
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