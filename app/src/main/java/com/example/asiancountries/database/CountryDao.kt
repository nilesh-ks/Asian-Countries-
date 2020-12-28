package com.example.asiancountries.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asiancountries.model.CountryEntity

@Dao
interface CountryDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCountry(countryEntity: CountryEntity)

    @Delete
    fun deleteCountry(countryEntity: CountryEntity)

    @Query("SELECT * FROM countryList")
    fun getAllCountries(): LiveData<List<CountryEntity>>

    /*@Query("DELETE FROM orders where resId= :resId")
    fun deleteOrders(resId: String)*/
    @Query("DELETE FROM countryList")
    suspend fun deleteAllUsers()
}