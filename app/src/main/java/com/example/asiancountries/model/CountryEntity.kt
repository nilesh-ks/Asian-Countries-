package com.example.asiancountries.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray


@Parcelize
@Entity(tableName = "countryList")
data class CountryEntity(
    @PrimaryKey val name:String,
    @ColumnInfo(name="capital")val capital: String,
    @ColumnInfo(name="region") val region: String,
    @ColumnInfo(name="subregion") val subregion: String,
    @ColumnInfo(name="population") val population: String,
    @ColumnInfo(name="borders") val borders: String,
    @ColumnInfo(name="languages") val languages: String,
    @ColumnInfo(name="flag") val imgFlag: String



): Parcelable