package com.example.redpacktest.api

import com.example.redpacktest.data.model.DrinkList
import com.example.redpacktest.data.model.GeoCode
import com.example.redpacktest.data.model.GeoCodeResponse
import retrofit2.http.*

interface WebService {
    @Headers("Content-Type: application/json")
    @POST("geocode")
    suspend fun getGeocode(@Body geoCodeRequest: String): GeoCodeResponse
    @GET("search.php")
    suspend fun getTragoByName(@Query("s") tragoName:String) : DrinkList
}