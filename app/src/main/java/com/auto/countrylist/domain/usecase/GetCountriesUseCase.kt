package com.auto.countrylist.domain.usecase

import com.auto.countrylist.common.Resource
import com.auto.countrylist.data.remote.dto.country.CountryDto
import com.auto.countrylist.domain.repository.CountriesRepository
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: CountriesRepository,
)
{
    operator fun invoke(): Flow<Resource<List<CountryDto>>> = flow {
        try
        {
            emit(Resource.Loading())
            val countries = repository.getCountries()
            emit(Resource.Success(countries))
        } catch (e: HttpException)
        {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException)
        {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}