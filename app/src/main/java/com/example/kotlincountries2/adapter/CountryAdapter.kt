package com.example.kotlincountries2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries2.R
import com.example.kotlincountries2.model.Country
import com.example.kotlincountries2.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(var view : View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {  //item_country ile adapter i birbirine bağlar
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country, parent, false)

        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {  //item_country nin componentlarına ulaşmak için
        holder.view.name.text = countryList[position].countryName  //countryList ten countryName i item_country deki name e atadık
        holder.view.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener {    //ülkelere basınca çalışır
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCountryList(newCountryList : List<Country>){  //yeni gelen verileri eskileri ile yer değiştirir
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}