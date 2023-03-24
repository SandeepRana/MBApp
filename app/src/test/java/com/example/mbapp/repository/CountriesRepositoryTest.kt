package com.example.mbapp.repository

import com.example.mbapp.api.MBApi
import com.example.mbapp.models.CommonModel
import com.example.mbapp.models.CountryModel
import com.example.mbapp.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesRepositoryTest {

    @Mock
    private lateinit var mbApi: MBApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCountries_expected_success_emptyList() = runTest {
        // Arrange
        Mockito.`when`(mbApi.getCountries()).thenReturn(Response.success(emptyList()))
        val repository = CountriesRepository(mbApi = mbApi)
        // Act
        val result = repository.getCountries()
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun getCountries_expected_success_withList() = runTest {
        // Arrange
        val list = listOf(
            CountryModel(name = CommonModel(common = "India", official = "")),
            CountryModel(name = CommonModel(common = "Canada", official = ""))
        )
        Mockito.`when`(mbApi.getCountries()).thenReturn(Response.success(list))
        val repository = CountriesRepository(mbApi = mbApi)
        // Act
        val result = repository.getCountries()
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(2, result.data!!.size)
        assertEquals("Canada", result.data!![1].name.common)
    }

    @Test
    fun getCountryDetails_expected_success_empty() = runTest {
        // Arrange
        Mockito.`when`(mbApi.getCountryDetails(country = "India"))
            .thenReturn(Response.success(emptyList()))
        val repository = CountriesRepository(mbApi = mbApi)
        // Act
        val result = repository.getCountryDetails(country = "India")
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun getCountries_expected_success_details() = runTest {
        // Arrange
        val list = listOf(
            CountryModel(name = CommonModel(common = "India", official = "India details")),
        )
        Mockito.`when`(mbApi.getCountryDetails(country = "India"))
            .thenReturn(Response.success(list))
        val repository = CountriesRepository(mbApi = mbApi)
        // Act
        val result = repository.getCountryDetails(country = "India")
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals("India details", result.data!![0].name.official)
    }
}