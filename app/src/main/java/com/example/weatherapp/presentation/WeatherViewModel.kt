package com.example.weatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherDataNetworkMiddleware
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {


    private val store = Store(
        initialState = WeatherState(),
        reducer = WeatherReducer(),
        middlewares = listOf(

            WeatherDataNetworkMiddleware(
                weatherRepository = repository,
            ),
        )
    )
    val viewState: StateFlow<WeatherState> = store.state

    fun loadWeatherInfo() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                val action = WeatherAction.FetchWeatherRequested(location.latitude, location.longitude)
                store.dispatch(action)
            }
        }
    }
}