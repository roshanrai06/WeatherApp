package com.example.weatherapp.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.weatherapp.database.WeatherDatabase
import com.example.weatherapp.database.cities.CityDatabaseDao
import com.example.weatherapp.database.weather.WeatherDatabaseDao
import com.example.weatherapp.network.WeatherApi

import com.example.weatherapp.ui.LocationManager
import com.example.weatherapp.ui.forecast.dataStore
import com.example.weatherapp.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun weatherDatabase(app: Application) = WeatherDatabase.getInstance(app)


    @Provides
    @Singleton
    fun cityDao(db: WeatherDatabase) = db.cityDatabaseDao

    @Provides
    @Singleton
    fun weatherDao(db: WeatherDatabase) = db.weatherDatabaseDao

    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun weatherApi(moshi: Moshi) = WeatherApi(moshi)

    @Provides
    @Singleton
    fun prefs(app: Application) = app.applicationContext.dataStore

    @Provides
    @Singleton
    fun weatherRepo(
        weatherDao: WeatherDatabaseDao,
        cityDao: CityDatabaseDao,
        api: WeatherApi,
        lm: LocationManager,
        prefs: DataStore<Preferences>
    ) =
        WeatherRepository(weatherDao, cityDao, api, lm, prefs)

    @Provides
    @Singleton
    fun locationProvider(app: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)


    @Provides
    @Singleton
    fun locationManager(
        fusedLocationClient: FusedLocationProviderClient,
    ) = LocationManager(fusedLocationClient)
}
