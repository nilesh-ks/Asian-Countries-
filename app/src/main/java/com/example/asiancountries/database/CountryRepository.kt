package com.example.asiancountries.database

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.asiancountries.model.CountryEntity


class CountryRepository(private val countryDao: CountryDao) {
    val getAllRecords: LiveData<List<CountryEntity>> =countryDao.getAllCountries()

    suspend fun insertCountry(countryEntity: CountryEntity)
    {
        countryDao.insertCountry(countryEntity)
    }
   /* suspend fun deleteAll(countryEntity: CountryEntity)
    {
        countryDao.deleteCountries()
    }*/

    suspend fun deleteAllUsers(){
        countryDao.deleteAllUsers()
    }

   // fun deleteCountries() { deleteAllWordsAsyncTask().execute() }
}