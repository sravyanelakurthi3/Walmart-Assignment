package com.example.walmart_coding_assessment.network

import com.example.walmart_coding_assessment.Country
import retrofit2.http.GET

/**
 * Retrofit service defining the endpoint to fetch the list of countries.
 * The path is relative to the base URL configured in the repository.  The
 * endpoint returns an array of JSON objects, which Retrofit will map
 * directly onto the [Country] data class.
 */
interface CountryService {
    @GET("peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountries(): List<Country>
}