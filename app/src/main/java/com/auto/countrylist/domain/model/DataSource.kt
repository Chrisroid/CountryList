package com.auto.countrylist.domain.model

class DataSource {

    fun loadLanguages(): List<LanguageModel> {
        return listOf(
            LanguageModel("Kotlin" , "") ,
            LanguageModel("Android" , "") ,
            LanguageModel("Git" , "") ,
            LanguageModel("Java" , "")
        )
    }
}