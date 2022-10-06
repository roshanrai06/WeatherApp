package com.example.weatherapp.presentation

import com.example.weatherapp.domain.weather.WeatherInfo
import com.example.weatherapp.redux.Action

sealed class WeatherAction : Action {
    data class FetchWeatherRequested(val lat: Double, val long: Double) : WeatherAction()
    object FetchWeatherStarted : WeatherAction()
    data class FetchWeatherStartedCompleted(val weatherInfo:WeatherInfo?) : WeatherAction()
    data class FetchWeatherFailed(val error: Throwable?) : WeatherAction()
}
