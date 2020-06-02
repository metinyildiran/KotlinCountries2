package com.example.kotlincountries2.service

import com.example.kotlincountries2.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryAPIService {

    //https://raw.githubusercontent.com/Qubits3/KotlinCountries/master/countrydataset.json
    //BASE_URL = https://raw.githubusercontent.com/

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())     //Gson kullanılacak
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())      //RxJava kullanılacak
        .build()
        .create(CountryAPI::class.java)     //countryAPI ı bağladık

    fun getData(): Single<List<Country>> {
        return api.getCountries()       //CountryAPI ın içindeki getCountries metodunu çağırdık
    }

}