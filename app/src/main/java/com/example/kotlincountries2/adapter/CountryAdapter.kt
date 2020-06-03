package com.example.kotlincountries2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries2.R
import com.example.kotlincountries2.databinding.ItemCountryBinding
import com.example.kotlincountries2.model.Country
import com.example.kotlincountries2.util.downloadFromUrl
import com.example.kotlincountries2.util.placeholderProgressBar
import com.example.kotlincountries2.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(var view : ItemCountryBinding) : RecyclerView.ViewHolder(view.root){    //root binding ile eşleşen view u döndürür

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {  //item_country ile adapter i birbirine bağlar
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country, parent, false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country, parent, false)  //item_country i bağladık, yukarıdakinin DataBinding hali
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {  //item_country nin componentlarına ulaşmak için

        holder.view.country = countryList[position]

        /*
        holder.view.name.text = countryList[position].countryName  //countryList ten countryName i item_country deki name e atadık
        holder.view.region.text = countryList[position].countryRegion

        holder.view.setOnClickListener {    //ülkelere basınca çalışır
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgressBar(holder.view.context))
        */
    }

    fun updateCountryList(newCountryList : List<Country>){  //yeni gelen verileri eskileri ile yer değiştirir
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}