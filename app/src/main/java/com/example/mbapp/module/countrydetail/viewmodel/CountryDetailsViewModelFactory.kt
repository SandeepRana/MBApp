package com.example.mbapp.module.countrydetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mbapp.repository.CountriesRepository
import javax.inject.Inject

class CountryDetailsViewModelFactory @Inject constructor(
    private val repository: CountriesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryDetailsViewModel(repository) as T
    }
}