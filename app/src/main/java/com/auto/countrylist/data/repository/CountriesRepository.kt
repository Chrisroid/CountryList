package com.auto.countrylist.data.repository

import com.auto.countrylist.data.remote.dto.country.CountryDto

interface CountriesRepository
{
    suspend fun getCountries() : List<CountryDto>

}