package com.example.mbapp.module.countrydetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mbapp.models.CountryModel
import com.example.mbapp.repository.CountriesRepository
import com.example.mbapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryDetailsViewModel @Inject constructor(
    private val repository: CountriesRepository
) : ViewModel() {

    private val _countryDetailsLiveData = MutableLiveData<NetworkResult<List<CountryModel>>>()
    val countryDetailsLiveData: LiveData<NetworkResult<List<CountryModel>>>
        get() = _countryDetailsLiveData

    /**
     * Getting country details from server
     */
    fun getCountryDetails(dispatcher: CoroutineDispatcher, country: String) {
        if (_countryDetailsLiveData.value?.data?.isNotEmpty() == true) return
        CoroutineScope(dispatcher).launch {
            _countryDetailsLiveData.postValue(NetworkResult.Loading())
            val result = repository.getCountryDetails(country = country)
            _countryDetailsLiveData.postValue(result)
        }
    }
}