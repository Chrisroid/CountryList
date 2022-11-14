package com.auto.countrylist.ui.language

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.auto.countrylist.adapter.LanguageAdapter
import com.auto.countrylist.databinding.FragmentLanguageListBinding
import com.auto.countrylist.domain.model.DataSource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LanguageModalSheet : BottomSheetDialogFragment() {

    private var _binding: com.auto.countrylist.databinding.FragmentLanguageListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LanguageAdapter
    private var languageList = DataSource().loadLanguages()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View
    {
        // Inflate the layout for this fragment
        _binding = FragmentLanguageListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        loadLanguages()

        binding.dismiss.setOnClickListener{
            dismiss()
        }
    }

    private fun loadLanguages(){
        adapter = LanguageAdapter {
        }
        adapter.submitList(languageList)
        binding.languageRecyclerview.layoutManager =
            LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
        binding.languageRecyclerview.adapter = adapter
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}