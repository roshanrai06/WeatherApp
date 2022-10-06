package com.example.weatherapp.presentation

import com.example.weatherapp.domain.weather.WeatherInfo
import com.example.weatherapp.redux.State

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val latitude:Double?=null,
    val longitude:Double?=null
): State