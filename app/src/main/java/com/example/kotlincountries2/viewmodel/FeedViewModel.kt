package com.example.kotlincountries2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountries2.model.Country

class FeedViewModel : ViewModel() {
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){

        val country = Country("Norway", "Europe", "Oslo", "NOK", "Norwegian", "www.website.no")
        val country2 = Country("Sweden", "Europe", "Stockholm", "SEK", "Swedish", "www.website.se")
        val country3 = Country("Turkey", "Asia", "Ankara", "TRY", "Turkish", "www.website.tr")

        val countryList = arrayListOf<Country>(country, country2, country3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

}