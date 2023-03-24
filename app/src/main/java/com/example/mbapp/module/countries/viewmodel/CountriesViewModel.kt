package com.example.mbapp.module.countries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mbapp.models.CountryModel
import com.example.mbapp.repository.CountriesRepository
import com.example.mbapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(private val repository: CountriesRepository) :
    ViewModel() {

    private val _countriesLiveData = MutableLiveData<NetworkResult<List<CountryModel>>>()
    val countriesLiveData: LiveData<NetworkResult<List<CountryModel>>>
        get() = _countriesLiveData

    /**
     * Getting all countries from server
     */
    fun getCountries(dispatcher: CoroutineDispatcher) {
        if (countriesLiveData.value?.data?.isNotEmpty() == true) return
        CoroutineScope(dispatcher).launch {
            _countriesLiveData.postValue(NetworkResult.Loading())
            val result = repository.getCountries()
            _countriesLiveData.postValue(result)
        }
    }
}