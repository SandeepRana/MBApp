package com.example.mbapp.module.countries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mbapp.repository.CountriesRepository
import javax.inject.Inject

class CountriesViewModelFactory @Inject constructor(private val repository: CountriesRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountriesViewModel(repository) as T
    }
}