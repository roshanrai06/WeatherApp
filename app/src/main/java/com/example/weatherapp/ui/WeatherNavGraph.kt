package com.example.weatherapp.ui


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.forecast.ForecastViewModel
import com.example.weatherapp.ui.city_select.CitySelect
import com.example.weatherapp.ui.city_select.CitySelectViewModel
import com.example.weatherapp.ui.forecast.Forecast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi


object MainDestinations {
    const val FORECAST_ROUTE = "forecast"
    const val CITY_SELECT_ROUTE = "city_select"
}

@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun WeatherNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.FORECAST_ROUTE,
    forecastViewModel: ForecastViewModel = viewModel(),
    citySelectViewModel: CitySelectViewModel = viewModel(),
    currentListState: LazyListState,
    hourlyListState: LazyListState,
    dailyListState: LazyListState,
    pagerState: PagerState,
    scaffoldState: ScaffoldState
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.FORECAST_ROUTE) {
            Forecast(
                vm = forecastViewModel,
                navController = navController,
                currentListState = currentListState,
                hourlyListState = hourlyListState,
                dailyListState = dailyListState,
                pagerState = pagerState,
                scaffoldState = scaffoldState
            )
        }
        composable(MainDestinations.CITY_SELECT_ROUTE) {
            CitySelect(
                vm = citySelectViewModel,
                navController = navController,
            )
        }
    }
}