package com.alefglobalintegralproductivityconsulting.alef_app.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alefglobalintegralproductivityconsulting.alef_app.R
import com.alefglobalintegralproductivityconsulting.alef_app.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var mBinding: FragmentFavoritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentFavoritesBinding.bind(view)
    }
}