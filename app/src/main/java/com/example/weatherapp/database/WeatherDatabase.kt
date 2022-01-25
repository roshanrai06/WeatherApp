package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.database.cities.*
import com.example.weatherapp.database.weather.*


const val DB_NAME = "weather_database"
const val DB_VERSION = 1

@Database(
    entities = [
        DbCurrent::class,
        DbHourly::class,
        DbDaily::class,
        DbAlert::class,
        DbCity::class,
        DbState::class,
        DbCountry::class,
        DbSavedCity::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val weatherDatabaseDao: WeatherDatabaseDao
    abstract val cityDatabaseDao: CityDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherDatabase::class.java,
                        DB_NAME
                    )
                        .createFromAsset("cities.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}