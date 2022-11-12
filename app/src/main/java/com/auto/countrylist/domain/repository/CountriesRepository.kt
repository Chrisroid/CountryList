package com.auto.countrylist.domain.repository

import com.auto.countrylist.data.remote.dto.country.CountryDto

interface CountriesRepository
{
    suspend fun getCountries() : List<CountryDto>

}