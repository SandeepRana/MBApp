package com.example.mbapp.module.countries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mbapp.databinding.CountryListItemBinding
import com.example.mbapp.models.CountryModel

class CountriesAdapter(
    private val countryList: List<CountryModel>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    class CountriesViewHolder(val binding: CountryListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryListItemBinding.inflate(layoutInflater, parent, false)
        return CountriesViewHolder(binding)
    }

    override fun getItemCount() = countryList.size

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        with(holder.binding) {
            val country = countryList[position].name.common
            countryTv.text = country
            root.setOnClickListener {
                // Country onClick listener
                onItemClick.invoke(country)
            }
        }
    }
}