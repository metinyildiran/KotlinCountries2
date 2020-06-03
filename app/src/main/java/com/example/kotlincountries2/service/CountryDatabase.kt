package com.example.kotlincountries2.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlincountries2.model.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDAO

    //Singleton -> sadece bir tane obje oluşturulabilir

    companion object {  //companion diğer yerlerden ulaşmak için

        @Volatile private var instance: CountryDatabase? = null     //Volatile diğer threadler tarafından görünür olması için

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){     //instance var mı yok mu kontrol ediliyor, varsa instance döndürülüyor, yoksa senkronize bir şekilde yeni bir tane oluşturuluyor
            instance ?: makeDatabase(context).also {
                instance = it   //CountryDatabase = instance yaptık
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(  //Database oluşturduk
            context.applicationContext,
            CountryDatabase::class.java, "countrydatabase"
        ).build()

    }

}