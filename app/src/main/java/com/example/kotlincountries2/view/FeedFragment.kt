package com.example.kotlincountries2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincountries2.R
import com.example.kotlincountries2.adapter.CountryAdapter
import com.example.kotlincountries2.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()     //internetten veri indirir

        countryList.layoutManager =
            LinearLayoutManager(context)    //linear layout manager item_country leri alta alta göstermeye olanak sağlar
        countryList.adapter = countryAdapter

        swipeRefreshLayout.setOnRefreshListener {   //swipeRefreshLayout için yenileme listenerı
            countryList.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.VISIBLE
            viewModel.refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {     //internetten inen veriyi ekrana basar
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            //FeedViewModel da countries List<Country> türünde bir değişken, o yüzden bize it, bir List<Country> döndürüyor
            it?.let {
                countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(it)    //internetten inmiş veriyi atar

            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    countryLoading.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE
                } else {
                    countryLoading.visibility = View.GONE
                }
            }
        })
    }

}
