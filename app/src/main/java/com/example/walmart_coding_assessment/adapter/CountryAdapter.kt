package com.example.walmart_coding_assessment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walmart_coding_assessment.Country
import com.example.walmart_coding_assessment.R

/**
 * Adapter for the RecyclerView displaying country information.  It leverages
 * ListAdapter and DiffUtil to minimise unnecessary UI updates.  Each
 * ViewHolder binds the data to the layout defined in `item_country.xml`.
 */
class CountryAdapter : ListAdapter<Country, CountryAdapter.CountryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameRegionText: TextView = itemView.findViewById(R.id.nameRegion)
        private val codeText: TextView = itemView.findViewById(R.id.code)
        private val capitalText: TextView = itemView.findViewById(R.id.capital)

        fun bind(country: Country) {
            // Compose the first row: "name", "region" on the left and "code" on the right.
            val name = country.name ?: ""
            val region = country.region ?: ""
            nameRegionText.text = listOfNotNull(name.takeIf { it.isNotBlank() }, region.takeIf { it.isNotBlank() })
                .joinToString(separator = ", ")
            codeText.text = country.code ?: ""
            // Second row is the capital.  If missing, leave it blank.
            capitalText.text = country.capital ?: ""
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            // Use the unique code as the identity if available; fall back to name.
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
}