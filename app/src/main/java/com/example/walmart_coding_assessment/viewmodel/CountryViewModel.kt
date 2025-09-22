package com.example.walmart_coding_assessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmart_coding_assessment.Country
import com.example.walmart_coding_assessment.repository.CountryRepository
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for orchestrating the retrieval of countries and
 * exposing them to the UI layer.  It retains its state across configuration
 * changes and ensures that only one network request is performed per
 * lifecycle unless explicitly refreshed.
 */
class CountryViewModel(private val repository: CountryRepository) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    /** Public immutable view of the country list. */
    val countries: LiveData<List<Country>> = _countries

    private val _error = MutableLiveData<String?>()
    /** Public immutable view of the error message; `null` when no error. */
    val error: LiveData<String?> = _error

    init {
        fetchCountries()
    }

    /**
     * Refresh the list of countries.  A new coroutine is launched in the
     * `viewModelScope`; the repository returns either a success or failure
     * which is then posted to the corresponding LiveData fields.
     */
    fun fetchCountries() {
        viewModelScope.launch {
            val result = repository.fetchCountries()
            result
                .onSuccess { list ->
                    _countries.value = list
                    _error.value = null
                }
                .onFailure { e ->
                    _countries.value = emptyList()
                    _error.value = e.localizedMessage ?: "Unknown error"
                }
        }
    }
}