package com.example.weatherapp

import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.presentation.WeatherAction
import com.example.weatherapp.presentation.WeatherState
import com.example.weatherapp.redux.Middleware
import com.example.weatherapp.redux.Store
import javax.inject.Inject

class WeatherDataNetworkMiddleware
@Inject constructor(private val weatherRepository: WeatherRepository) :
    Middleware<WeatherState, WeatherAction> {
    override suspend fun process(
        action: WeatherAction,
        currentState: WeatherState,
        store: Store<WeatherState, WeatherAction>
    ) {
        when (action) {
            is WeatherAction.FetchWeatherRequested -> {
                getWeatherData(store, action)
            }


            else -> {}
        }
    }

    private suspend fun getWeatherData(
        store: Store<WeatherState, WeatherAction>,
        action: WeatherAction.FetchWeatherRequested
    ) {

        store.dispatch(WeatherAction.FetchWeatherStarted)

        val isSuccessful = weatherRepository.getWeatherData(
            lat = action.lat,
            long = action.long,
        )



        if (isSuccessful.data != null) {
            store.dispatch(WeatherAction.FetchWeatherStartedCompleted(isSuccessful.data))
        } else {
            store.dispatch(WeatherAction.FetchWeatherFailed(null))
        }
    }

}