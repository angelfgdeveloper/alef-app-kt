package com.companyglobal.alef_app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.companyglobal.alef_app.core.AppConstants
import com.companyglobal.alef_app.core.Result
import com.companyglobal.alef_app.data.model.Vacant
import com.companyglobal.alef_app.data.model.VacantInfoExtra
import com.companyglobal.alef_app.data.remote.home.RemoteHomeDataSource
import com.companyglobal.alef_app.databinding.ActivitySearchBinding
import com.companyglobal.alef_app.domain.home.HomeRepoImpl
import com.companyglobal.alef_app.presentation.home.HomeViewModel
import com.companyglobal.alef_app.presentation.home.HomeViewModelFactory
import com.companyglobal.alef_app.ui.fragments.home.adapter.VacantAdapter
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
        setupSearching()
    }

    private fun setupSearching() {
        with(mBinding) {
            ivBackPress.setOnClickListener { finish() }
            ivFilter.setOnClickListener {
                val i = Intent(applicationContext, FilterActivity::class.java)
                startActivity(i)
            }

            val search = intent.getStringExtra(AppConstants.SEARCH_GENERAL)
            val location = intent.getStringExtra(AppConstants.SEARCH_LOCATION)

            if (search!!.isNotEmpty() && location!!.isNotEmpty()) {
                etSearch.setText("$search - $location")
                setupVacancies("$search - $location")
            } else if (search.isNotEmpty()) {
                etSearch.setText("$search")
                setupVacancies("$search")
            } else if (location!!.isNotEmpty()) {
                etSearch.setText("$location")
                setupVacancies("$location")
            } else {
                etSearch.setText("Todo")
                setupVacancies()
            }

            etSearch.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val word = etSearch.text.toString().trim().lowercase()
                    if (word != "Todo") {
                        setupVacancies(word)
                    } else {
                        setupVacancies()
                    }
                    handled = true
                }
                handled
            }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().isEmpty()) {
                        setupVacancies()
                    }
                }

            })

        }
    }

    private fun setupVacancies(word: String = "Todo") {
        hideKeyboard()

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

                    if (word != "Todo" && word.isNotEmpty()) {
                        setFilter(word, result.data)
                    }

                    mBinding.rvVacancies.adapter = mAdapter
                }
            }
        }
    }

    private fun setFilter(word: String, vacants: List<Vacant>) {
        val newVacants: ArrayList<Vacant> = ArrayList()
        for (w in vacants) {
            if (w.title.trim().lowercase().contains(word.lowercase())) {
                newVacants.add(w)
            }
            if (w.location.trim().lowercase().contains(word.lowercase())) {
                newVacants.add(w)
            }
        }
        mAdapter.setFilter(newVacants)
    }

    override fun onVacantClick(vacant: Vacant, vacantInfoExtra: VacantInfoExtra?) {
        val gson = Gson()
        val jsonVacant = gson.toJson(vacant)
        val jsonVacantInfoExtra = gson.toJson(vacantInfoExtra)

        val i = Intent(SearchActivity@this, HomeActivity::class.java)
        i.putExtra(AppConstants.JSON_VACANT, jsonVacant)
        i.putExtra(AppConstants.JSON_DETAILS_VACANT, jsonVacantInfoExtra)
        startActivity(i)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(mBinding.root.windowToken, 0)
    }
}