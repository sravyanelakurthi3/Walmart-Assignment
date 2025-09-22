package com.example.walmart_coding_assessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.walmart_coding_assessment.repository.CountryRepository

/**
 * Factory for creating [CountryViewModel] instances with a provided
 * [CountryRepository].  This allows the view model to be instantiated
 * cleanly without relying on a global singleton.
 */
class CountryViewModelFactory(private val repository: CountryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}