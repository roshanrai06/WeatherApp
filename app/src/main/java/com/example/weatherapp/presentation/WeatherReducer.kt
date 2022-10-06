package com.example.weatherapp.presentation

import com.example.weatherapp.redux.Reducer


class WeatherReducer : Reducer<WeatherState, WeatherAction> {

    /**
     * Side note: Notice that all of the functions are named in a way that they signify they're
     * returning a new state, and not just processing information. This helps keep your when statements
     * clear that they're returning stuff, so that context isn't lost.
     */
    override fun reduce(currentState: WeatherState, action: WeatherAction): WeatherState {
        return when (action) {
            is WeatherAction.FetchWeatherStarted -> {
                stateWeatherFetch(currentState)
            }
            is WeatherAction.FetchWeatherStartedCompleted -> {
                stateWeatherFetchCompleted(currentState,action)
            }
            WeatherAction.FetchWeatherFailed(null) -> {
                stateWithFetchWeatherError(currentState)
            }

            else -> currentState
        }
    }

    private fun stateWithFetchWeatherError(currentState: WeatherState) =
        currentState.copy(
            isLoading = false,
            error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
        )

    private fun stateWeatherFetch(currentState: WeatherState) =
        currentState.copy(
            isLoading = true,
        )

    private fun stateWeatherFetchCompleted(
        currentState: WeatherState,
        action: WeatherAction.FetchWeatherStartedCompleted
    ) =
        currentState.copy(
            isLoading = false,
            weatherInfo = action.weatherInfo
        )



}