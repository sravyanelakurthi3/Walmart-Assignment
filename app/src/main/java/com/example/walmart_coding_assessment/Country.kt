package com.example.walmart_coding_assessment

/**
 * Simple data class representing a country.  The properties mirror the fields
 * provided by the assessment JSON.  All values are nullable to gracefully
 * handle missing fields.
 */
data class Country(
    val name: String?,
    val region: String?,
    val code: String?,
    val capital: String?
)