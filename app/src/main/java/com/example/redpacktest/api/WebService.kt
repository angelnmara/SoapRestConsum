package com.example.redpacktest.api

import com.example.redpacktest.data.model.DrinkList
import com.example.redpacktest.data.model.GeoCode
import com.example.redpacktest.data.model.GeoCodeRequest
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WebService {
    @Headers("Content-Type: application/json")
    @POST("geocode")
    suspend fun getGeocode(@Body geoCodeRequest: String): GeoCode
    @GET("search.php")
    suspend fun getTragoByName(@Query("s") tragoName:String) : DrinkList
}