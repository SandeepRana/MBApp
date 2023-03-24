package com.example.mbapp.module.countrydetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mbapp.databinding.ActivityCountryDetailsBinding
import com.example.mbapp.module.countrydetail.viewmodel.CountryDetailsViewModel
import com.example.mbapp.module.countrydetail.viewmodel.CountryDetailsViewModelFactory
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
class CountryDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: CountryDetailsViewModel
    private lateinit var binding: ActivityCountryDetailsBinding

    @Inject
    lateinit var repository: CountriesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val country = intent.getStringExtra(Common.COUNTRY) ?: ""
        val factory = CountryDetailsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CountryDetailsViewModel::class.java]

        // Initializing observer
        initObserver()

        // Getting country details from server
        viewModel.getCountryDetails(dispatcher = Dispatchers.IO, country = country)
    }

    /**
     * Observing Api response once server request will be completed
     */
    private fun initObserver() {
        viewModel.countryDetailsLiveData.observe(this) {
            when (it) {
                is NetworkResult.Error -> {
                    it.message?.toast(this)
                    hideLoader()
                }
                is NetworkResult.Loading -> showLoader()
                is NetworkResult.Success -> {
                    hideLoader()
                    if (!it.data.isNullOrEmpty()) {
                        binding.officialTv.text = it.data[0].name.official
                    }
                }
            }
        }
    }

    /**
     *  Showing loader
     */
    private fun showLoader() = binding.progressBar.show()

    /**
     *  Showing loader
     */
    private fun hideLoader() = binding.progressBar.hide()
}