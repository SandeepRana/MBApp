package com.example.mbapp.api

import com.example.mbapp.models.CountryModel
import com.example.mbapp.utils.constants.NetworkConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MBApi {

    @GET(NetworkConstants.ALL_COUNTRIES)
    suspend fun getCountries(): Response<List<CountryModel>>

    @GET(NetworkConstants.COUNTRY_DETAILS)
    suspend fun getCountryDetails(@Path(NetworkConstants.NAME) country: String): Response<List<CountryModel>>
}