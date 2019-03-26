package com.steven.coolweather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.steven.coolweather.data.db.bean.place.City
import com.steven.coolweather.data.db.bean.place.Country
import com.steven.coolweather.data.db.bean.place.Province

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */

@Database(
    entities = [
        Country::class,
        Province::class,
        City::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {

        private const val DB_NAME = "app_db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
        }

    }
}