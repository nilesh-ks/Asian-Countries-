package com.example.asiancountries.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asiancountries.model.CountryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [CountryEntity::class], version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {


    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: CountryDatabase? = null


        fun getDatabase(context: Context): CountryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountryDatabase::class.java,
                    "country_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }



    }

}
