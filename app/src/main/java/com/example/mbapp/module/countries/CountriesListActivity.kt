package com.example.mbapp.module.countries

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mbapp.databinding.ActivityCountryListBinding
import com.example.mbapp.module.countries.adapter.CountriesAdapter
import com.example.mbapp.module.countrydetail.CountryDetailsActivity
import com.example.mbapp.module.countries.viewmodel.CountriesViewModel
import com.example.mbapp.module.countries.viewmodel.CountriesViewModelFactory
import com.example.mbapp.repository.CountriesRepository
import com.example.mbapp.utils.NetworkResult
import com.example.mbapp.utils.constants.Common
import com.example.mbapp.utils.hide
import com.example.mbapp.utils.show
import com.example.mbapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@AndroidEntryPoint
class CountriesListActivity : AppCompatActivity() {

    private lateinit var viewModel: CountriesViewModel
    private lateinit var binding: ActivityCountryListBinding
    private lateinit var countriesAdapter: CountriesAdapter

    @Inject
    lateinit var repository: CountriesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = CountriesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CountriesViewModel::class.java]

        // Initializing observer
        initObserver()

        // Getting all countries from server
        viewModel.getCountries(Dispatchers.IO)
    }

    /**
     * Observing Api response once server request will be completed
     */
    private fun initObserver() {
        viewModel.countriesLiveData.observe(this) {
            when (it) {
                is NetworkResult.Error -> {
                    it.message?.toast(this)
                    hideLoader()
                }
                is NetworkResult.Loading -> showLoader()
                is NetworkResult.Success -> {
                    hideLoader()
                    // Initializing country list adapter
                    countriesAdapter = CountriesAdapter(it.data ?: emptyList(), ::onItemClick)
                    binding.recyclerView.adapter = countriesAdapter
                }
            }
        }
    }

    /**
     *  Showing loader
     */
    private fun showLoader() = binding.progressBar.show()

    /**
     *  Dismiss loader
     */
    private fun hideLoader() = binding.progressBar.hide()

    /**
     * Recyclerview onItem click listener
     */
    private fun onItemClick(country: String) {
        val intent = Intent(this, CountryDetailsActivity::class.java).apply {
            putExtra(Common.COUNTRY, country)
        }
        startActivity(intent)
    }
}