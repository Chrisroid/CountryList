package com.auto.countrylist.domain.model

import com.auto.countrylist.data.remote.dto.country.CountryDto

data class CountriesModel(var name: String, var countryDto: CountryDto, var isSection: Boolean)
