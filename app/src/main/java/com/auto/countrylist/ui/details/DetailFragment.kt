package com.auto.countrylist.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.auto.countrylist.data.remote.dto.country.CountryDto
import com.auto.countrylist.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso


class DetailFragment : Fragment()
{

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View?
    {

        _binding = FragmentDetailBinding.inflate(inflater , container , false)
        return binding.root

    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)

        val country = safeArgs.country
        bind(countryDto = country)
    }

    private fun bind(countryDto: CountryDto?)
    {
        binding.apply {
            if (countryDto != null)
            {
                Picasso.get().load(countryDto.flags?.png).into(countryFlagImage)
                population.text = countryDto.population.toString()
                region.text = countryDto.region
                capital.text = countryDto.capital?.first() ?: ""
                motto.text = null
                officialLang.text = countryDto.languages?.values?.first() ?: "Not available"
                ethnic.text = null
                religion.text = null
                government.text = null
                independence.text = countryDto.independent.toString()
                area.text = "${countryDto.area}Km2"
                currency.text = countryDto.currencies?.values?.first()?.name ?: ""
                gdp.text = null
                timezone.text = countryDto.timezones?.first() ?: ""
                dateFormat.text = null
                dialingCode.text = "${countryDto.idd?.root ?: ""}${countryDto.idd?.suffixes?.first() ?: ""}"
                drivingSide.text = countryDto.car?.side ?: ""
            }
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}