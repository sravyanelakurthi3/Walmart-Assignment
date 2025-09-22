package com.example.walmart_coding_assessment.repository

import com.example.walmart_coding_assessment.Country
import com.example.walmart_coding_assessment.network.CountryService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Repository responsible for fetching countries from the network.  It hides
 * the Retrofit setup from the view model and returns a [Result] wrapper to
 * propagate either a successful list of [Country] objects or the thrown
 * exception.  The base URL is set to the host of the provided gist; the
 * remaining path is specified in [CountryService].
 */
class CountryRepository {
    private val service: CountryService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(CountryService::class.java)
    }

    /**
     * Fetch the list of countries.  Any thrown exceptions are captured and
     * wrapped in a [Result.failure] so that callers can handle them uniformly.
     */
    suspend fun fetchCountries(): Result<List<Country>> {
        return try {
            val countries = service.getCountries()
            Result.success(countries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}