package com.auto.countrylist.data.repository

import com.auto.countrylist.data.remote.CountriesApi
import com.auto.countrylist.data.remote.dto.country.CountryDto
import com.auto.countrylist.domain.repository.CountriesRepository
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val api : CountriesApi
): CountriesRepository
{
    override suspend fun getCountries(): List<CountryDto>
    {
        return api.getCountries().body()!!
    }
}