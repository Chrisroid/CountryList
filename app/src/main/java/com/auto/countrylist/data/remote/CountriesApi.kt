package com.auto.countrylist.data.remote

import retrofit2.Response
import retrofit2.http.GET
import com.auto.countrylist.data.remote.dto.country.CountryDto

interface CountriesApi
{
    @GET("all")
    suspend fun getCountries(): Response<List<CountryDto>>
}