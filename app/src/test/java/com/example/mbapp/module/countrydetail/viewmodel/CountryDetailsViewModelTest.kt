package com.example.mbapp.module.countrydetail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mbapp.models.CommonModel
import com.example.mbapp.models.CountryModel
import com.example.mbapp.repository.CountriesRepository
import com.example.mbapp.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailsViewModelTest {
    @Mock
    private lateinit var repository: CountriesRepository
    private val dispatcher = StandardTestDispatcher()

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun getCountryDetails_expected_success_empty() = runTest {
        // Arrange
        Mockito.`when`(repository.getCountryDetails(country = "India"))
            .thenReturn(NetworkResult.Success(data = emptyList()))
        val viewModel = CountryDetailsViewModel(repository = repository)
        // Act
        viewModel.getCountryDetails(dispatcher = dispatcher, country = "India")
        dispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.countryDetailsLiveData.value
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result?.data!!.size)
    }

    @Test
    fun getCountryDetails_expected_success_details() = runTest {
        // Arrange
        val list = listOf(CountryModel(CommonModel("India", "India details")))
        Mockito.`when`(repository.getCountryDetails(country = "India"))
            .thenReturn(NetworkResult.Success(data = list))
        val viewModel = CountryDetailsViewModel(repository = repository)
        // Act
        viewModel.getCountryDetails(dispatcher = dispatcher, country = "India")
        dispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.countryDetailsLiveData.value
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals("India details", result?.data!![0].name.official)
    }

    @Test
    fun getCountryDetails_expected_error() = runTest {
        // Arrange
        Mockito.`when`(repository.getCountryDetails(country = "India"))
            .thenReturn(NetworkResult.Error(message = "Something went wrong!"))
        val viewModel = CountryDetailsViewModel(repository = repository)
        // Act
        viewModel.getCountryDetails(dispatcher = dispatcher, country = "India")
        dispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.countryDetailsLiveData.value
        // Assert
        assertEquals(true, result is NetworkResult.Error)
        assertEquals("Something went wrong!", result?.message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}