package com.example.mbapp.module.countries.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mbapp.getOrAwaitValue
import com.example.mbapp.models.CommonModel
import com.example.mbapp.models.CountryModel
import com.example.mbapp.repository.CountriesRepository
import com.example.mbapp.utils.NetworkResult
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class CountriesViewModelTest {
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
    fun getCountries_expected_success_emptyList() = runTest {
        // Arrange
        Mockito.`when`(repository.getCountries())
            .thenReturn(NetworkResult.Success(data = emptyList()))
        val viewModel = CountriesViewModel(repository = repository)
        // Act
        viewModel.getCountries(dispatcher = dispatcher)
        dispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.countriesLiveData.getOrAwaitValue()
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun getCountries_expected_success_withList() = runTest {
        // Arrange
        val list = listOf(
            CountryModel(CommonModel("India", "")),
            CountryModel(CommonModel("Canada", "")),
        )
        Mockito.`when`(repository.getCountries()).thenReturn(NetworkResult.Success(data = list))
        val viewModel = CountriesViewModel(repository = repository)
        // Act
        viewModel.getCountries(dispatcher = dispatcher)
        dispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.countriesLiveData.getOrAwaitValue()
        // Assert
        assertEquals(true, result is NetworkResult.Success)
        assertEquals(2, result.data!!.size)
        assertEquals("India", result.data!![0].name.common)
    }

    @Test
    fun getCountries_expected_error() = runTest {
        // Arrange
        Mockito.`when`(repository.getCountries())
            .thenReturn(NetworkResult.Error(message = "Something went wrong!"))
        val viewModel = CountriesViewModel(repository = repository)
        // Act
        viewModel.getCountries(dispatcher = dispatcher)
        dispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.countriesLiveData.getOrAwaitValue()
        // Assert
        assertEquals(true, result is NetworkResult.Error)
        assertEquals("Something went wrong!", result.message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}