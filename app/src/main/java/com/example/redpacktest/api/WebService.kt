package com.example.redpacktest.api

import com.example.redpacktest.data.model.DrinkList
import com.example.redpacktest.data.model.GeoCode
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("geocode")
    suspend fun getGeocode():List<GeoCode>
    @GET("search.php")
    suspend fun getTragoByName(@Query("s") tragoName:String) : DrinkList
}