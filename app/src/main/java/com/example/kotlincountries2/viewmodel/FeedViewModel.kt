package com.example.kotlincountries2.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotlincountries2.model.Country
import com.example.kotlincountries2.service.CountryAPIService
import com.example.kotlincountries2.service.CountryDatabase
import com.example.kotlincountries2.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {   //internetten veri indirir
        countryLoading.value = true     //ekranda progress bar gözüksün

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {      //getData başarılı ise çalıştır
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>) {  //internetten gelen verileri countries e atar
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list: List<Country>) {
        //main thread i bloklamadan, arkaplanda, işlem yap
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            //insertAll bize bir uuid listesi döndürdü, onu da ListLong a atadık, toTypedArray listeleri tekil hale getirir, * ise array i vararg a paslamak için kullanılır

            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }

            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())
    }
}