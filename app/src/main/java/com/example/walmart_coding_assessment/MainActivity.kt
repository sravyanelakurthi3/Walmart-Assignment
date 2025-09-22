package com.example.walmart_coding_assessment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmart_coding_assessment.adapter.CountryAdapter
import com.example.walmart_coding_assessment.repository.CountryRepository
import com.example.walmart_coding_assessment.viewmodel.CountryViewModel
import com.example.walmart_coding_assessment.viewmodel.CountryViewModelFactory

/**
 * The single activity of the application.  It sets up a RecyclerView and
 * observes the [CountryViewModel] for data and errors.  If an error occurs,
 * the user is notified via a simple Toast message.
 */
class MainActivity : AppCompatActivity() {
    // Lazily create the view model using the factory so that the repository
    // dependency can be provided.  The view model will survive configuration
    // changes automatically.
    private val viewModel: CountryViewModel by viewModels {
        CountryViewModelFactory(CountryRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = CountryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe the list of countries and submit it to the adapter when it changes.
        viewModel.countries.observe(this) { list ->
            adapter.submitList(list)
        }

        // Observe errors and show a toast.  In a real application you might
        // present a Snackbar with a retry action.
        viewModel.error.observe(this) { message ->
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}