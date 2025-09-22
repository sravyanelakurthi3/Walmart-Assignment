# Walmart Coding Assessment – Android

This repository contains a sample solution for the Walmart **Software Engineer – Mobile (Android)** coding assessment.  The goal of the exercise is to fetch a list of countries from a remote JSON endpoint and present them in a scrollable list on the device.  Each list item displays the country's name, region, alpha‑2 code and capital in a tidy layout.  The implementation emphasises clean, maintainable code rather than sheer feature count.

## Project structure

The solution is implemented as a simple Kotlin Android project using modern architecture components:

| Path | Purpose |
| --- | --- |
| `app/src/main/java/com/example/walmart_coding_assessment/Country.kt` | Data model representing a single country. |
| `app/src/main/java/com/example/walmart_coding_assessment/network/CountryService.kt` | Retrofit service definition to fetch the JSON list of countries. |
| `app/src/main/java/com/example/walmart_coding_assessment/repository/CountryRepository.kt` | Repository encapsulating the network call and returning a `Result` wrapper to the view model. |
| `app/src/main/java/com/example/walmart_coding_assessment/viewmodel/CountryViewModel.kt` | ViewModel that orchestrates fetching data and exposes it via `LiveData`.  Using a ViewModel ensures the list survives configuration changes such as device rotation. |
| `app/src/main/java/com/example/walmart_coding_assessment/viewmodel/CountryViewModelFactory.kt` | Factory for instantiating the view model with a repository dependency. |
| `app/src/main/java/com/example/walmart_coding_assessment/MainActivity.kt` | Host activity that observes the view model and binds the data to a `RecyclerView`. |
| `app/src/main/java/com/example/walmart_coding_assessment/adapter/CountryAdapter.kt` | A `ListAdapter` implementation that efficiently displays country items. |
| `app/src/main/res/layout/activity_main.xml` | Layout for the activity containing only a `RecyclerView`. |
| `app/src/main/res/layout/item_country.xml` | Layout for an individual row in the list, matching the format described in the assessment. |

The network dependency is handled by **Retrofit** with a **Gson** converter.  Asynchronous calls are performed in a coroutine using the `viewModelScope` and results are delivered back to the UI via `LiveData`.  Error conditions propagate through the repository as `Result` objects, allowing the activity to display an error message (for example via `Toast` or `Snackbar`).

## How it works

1. **Model** – `Country.kt` defines a simple Kotlin data class that mirrors the JSON fields provided by the assessment URL.
2. **Service** – `CountryService.kt` specifies a single `@GET` endpoint pointing at the provided gist URL.  Using Retrofit hides the low‑level networking details.
3. **Repository** – `CountryRepository.kt` constructs a Retrofit instance with a base URL of `https://gist.githubusercontent.com/` and exposes a `fetchCountries()` method.  This method returns a `Result<List<Country>>` so that both success and error states are captured.
4. **ViewModel** – `CountryViewModel.kt` holds the list of countries in a `MutableLiveData<List<Country>>` and an optional error message.  The `init` block triggers a call to the repository, ensuring that data is loaded when the activity is created.  Re‑invoking `fetchCountries()` allows the user to retry after an error.
5. **Activity** – `MainActivity.kt` sets up the `RecyclerView` with a linear layout manager and binds a `CountryAdapter`.  It observes the view model and updates the adapter when new data arrives.  The activity also listens to errors and can inform the user if the network request fails.
6. **Adapter** – `CountryAdapter.kt` extends `ListAdapter` and uses a `DiffUtil.ItemCallback` to efficiently update the list.  Each `CountryViewHolder` binds the data to three text views: the top row shows the "name" and "region" concatenated, the top right shows "code", and the second row displays "capital".

## Error handling & rotation support

The design makes use of a `ViewModel` to retain the list of countries across configuration changes, such as device rotation.  Because the `RecyclerView.Adapter` is fed directly from the `LiveData` in the view model, there is no need to refetch data when the device rotates.  Any exceptions thrown during the network call are caught in the repository and surfaced back to the UI via an `error` `LiveData` property, enabling the UI to display an appropriate message.

## Build & run

To use this code in an Android Studio project:

1. Create a new empty Android project targeting API 21 or higher.
2. Copy the contents of the `walmart_coding_assessment` directory into your project’s root (adjust the package name if needed).
3. Add the following dependencies to your `app/build.gradle`:

```gradle
implementation "androidx.appcompat:appcompat:1.6.1"
implementation "androidx.constraintlayout:constraintlayout:2.1.4"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.6.1"
implementation "androidx.recyclerview:recyclerview:1.3.1"
implementation "com.google.android.material:material:1.9.0"
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
```

3. Synchronise your project and run it on an emulator or device.  When launched, the application will fetch the list of countries from the provided gist and display them in a scrolling list.

## Notes

- The project intentionally keeps the UI simple to focus on the quality of the code rather than visual flourish.  Developers may choose to enhance the UI by adding loading indicators, swipe‑to‑refresh and better error handling as time permits.
- If the provided gist is unreachable or returns malformed JSON, the `Result.failure` branch in `CountryRepository` will trigger.  You can extend this basic error handling to retry automatically or to cache previous data.