package com.example.mbapp.repository

import com.example.mbapp.R
import com.example.mbapp.api.MBApi
import com.example.mbapp.app.MBApplication
import com.example.mbapp.models.CountryModel
import com.example.mbapp.utils.NetworkResult
import javax.inject.Inject

class CountriesRepository @Inject constructor(private val mbApi: MBApi) {

    /**
     * Getting all countries from server
     */
    suspend fun getCountries(): NetworkResult<List<CountryModel>> {
        val response = mbApi.getCountries()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error(message = MBApplication.appContext.getString(R.string.something_went_wrong))
            }
        } else {
            NetworkResult.Error(message = MBApplication.appContext.getString(R.string.something_went_wrong))
        }
    }

    /**
     * Getting country details from server based on specific country
     */
    suspend fun getCountryDetails(country: String): NetworkResult<List<CountryModel>> {
        val response = mbApi.getCountryDetails(country)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error(message = MBApplication.appContext.getString(R.string.something_went_wrong))
            }
        } else {
            NetworkResult.Error(message = MBApplication.appContext.getString(R.string.something_went_wrong))
        }
    }
}
