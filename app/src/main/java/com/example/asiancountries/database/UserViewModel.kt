package com.example.asiancountries.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.asiancountries.model.CountryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class UserViewModel(application: Application): AndroidViewModel(application) {

    val getAllCountries: LiveData<List<CountryEntity>>

    private val repository: CountryRepository

    init{
        val countryDao=CountryDatabase.getDatabase(application).countryDao()
        repository= CountryRepository(countryDao)
        getAllCountries=repository.getAllRecords


    }

    fun insertCountry(countryEntity: CountryEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertCountry(countryEntity)
        }
    }
    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }



}