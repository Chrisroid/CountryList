package com.auto.countrylist.ui.overview

import com.auto.countrylist.data.remote.dto.country.CountryDto

data class CountryListState(
    val isLoading: Boolean = false,
    val country: List<CountryDto> = emptyList(),
    val error: String = ""
)