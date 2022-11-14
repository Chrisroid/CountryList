package com.auto.countrylist.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import android.text.TextUtils
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.auto.countrylist.adapter.CountriesAdapter
import com.auto.countrylist.data.remote.dto.country.CountryDto
import com.auto.countrylist.databinding.FragmentOverviewBinding
import com.auto.countrylist.ui.language.LanguageModalSheet
import com.auto.countrylist.domain.model.CountriesModel
import com.auto.countrylist.ui.filter_list.FilterModalSheet
import com.auto.countrylist.ui.filter_list.FilterViewModel
import com.auto.countrylist.ui.viewmodel.OverviewViewModel
import com.auto.countrylist.utils.ConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private val countryListViewModel by viewModels<OverviewViewModel>()
    private lateinit var countryAdapter: CountriesAdapter
    private var countriesList = mutableListOf<CountryDto>()

    private val filterViewModel : FilterViewModel by activityViewModels()


    private var mSectionList: ArrayList<CountriesModel>? = null

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    private var dayNightMode = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            dayNightMode = !dayNightMode
            binding.apply {
                dayButton.visibility = View.VISIBLE
                nightButton.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                dayButton.visibility = View.GONE
                nightButton.visibility = View.VISIBLE
            }
        }

        connectivityObserver.observeNetworkStatus().asLiveData().observe(viewLifecycleOwner) {
            it?.let {
                if (it.name == "Lost") {
                    binding.countriesRecyclerview.visibility = View.INVISIBLE
                    binding.noInternetConnection.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    //         Handler(Looper.getMainLooper()).postDelayed({loadCards() } , 4000)
                    binding.countriesRecyclerview.visibility = View.VISIBLE
                    binding.noInternetConnection.visibility = View.INVISIBLE
                    loadCountry()
                    setUpSearchView()
                }
            }
        }

        binding.apply {
            language.setOnClickListener {
                showLanguageModal()
            }
            dayButton.setOnClickListener {
                setDayNightTheme(dayNightMode)
            }
            nightButton.setOnClickListener {
                setDayNightTheme(dayNightMode)
            }
        }
    }

    private fun showData(){
        lifecycleScope.launch {
            countryListViewModel.state.collect { it ->
                it.country.let { countries ->
                    val countriesModels: ArrayList<CountriesModel> = ArrayList()
                    countriesList = countries.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name.official!! }).toMutableList()
                    for (i in 0 until countriesList.size)
                    {
                        countriesModels.add(CountriesModel(countriesList[i].name.official!!,countriesList[i] , false))
                    }

                    mSectionList = ArrayList()
                    getHeaderListLatter(countriesModels)
                    countryAdapter = CountriesAdapter(mSectionList!!){ model ->
                        val action = OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(model.countryDto)
                        findNavController().navigate(action)
                    }
                    binding.countriesRecyclerview.layoutManager =
                        LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
                    binding.countriesRecyclerview.adapter = countryAdapter
                }
            }
        }
    }

    private fun setDayNightTheme(dayNight: Boolean) {
        if (dayNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Toast.makeText(requireContext(), "Night mode on", Toast.LENGTH_SHORT).show()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Toast.makeText(requireContext(), "Night mode off", Toast.LENGTH_SHORT).show()
        }
        dayNightMode = !dayNightMode
    }

    private fun setUpSearchView() {
        binding.searchList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                countryAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                countryAdapter.getFilter().filter(newText)
                return true
            }

        })
    }

    private fun showLanguageModal() {
        val languageModalSheet = LanguageModalSheet()
        languageModalSheet.show(requireFragmentManager(), LanguageModalSheet.TAG)
    }

    private fun showFilterModal(){
        val filterModalSheet = FilterModalSheet()
        filterModalSheet.show(requireFragmentManager() , FilterModalSheet.TAG)
    }


    private fun loadCountry(){
        filterViewModel.filter.observe(viewLifecycleOwner){ filters ->
            filters?.let {
                if (filters.isEmpty()){
                    showData()
                }else{
                    countryAdapter.getFilter().filter(filters.first().childTitle)
                }
            }
        }
    }

    private fun getHeaderListLatter(countryList: ArrayList<CountriesModel>)
    {
        countryList.sortWith(Comparator { country1 , country2 ->
            country1?.countryDto?.name?.official?.uppercase(Locale.getDefault())
                ?.compareTo(country2?.countryDto?.name?.official.toString().uppercase(
                    Locale.getDefault())) ?: 0
        })

        var lastHeader: String? = ""
        val size: Int = countryList.size
        for (i in 0 until size)
        {
            val user = countryList[i]
            val header = user.countryDto.name.official?.toCharArray()?.first()?.uppercase(Locale.getDefault())
            if (!TextUtils.equals(lastHeader , header))
            {
                lastHeader = header
                mSectionList!!.add(CountriesModel(header!!, user.countryDto , true))
            }
            mSectionList!!.add(user)
        }
    }


    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }