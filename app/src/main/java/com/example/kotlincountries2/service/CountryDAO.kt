package com.example.kotlincountries2.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountries2.model.Country

@Dao
interface CountryDAO {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>
    //Insert -> INSERT INTO
    //suspend -> coroutine içerisinde çağırılır ve duraklatılıp devam ettirilebilir
    //vararg -> multiple country objects
    //List<Long> -> primary keys döndürüyor

    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}