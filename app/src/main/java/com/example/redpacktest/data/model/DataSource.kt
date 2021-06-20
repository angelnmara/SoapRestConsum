package com.example.redpacktest.data.model

import com.example.redpacktest.api.RetrofitClient
import com.example.redpacktest.tools.Resource

class DataSource {
    suspend fun getGeoCode():Resource<List<GeoCode>>{
        return Resource.Success(RetrofitClient.webservice.getGeocode())
    }
    suspend fun getTrago(tragoName:String):Resource<List<Drink>>{
        return Resource.Success(RetrofitClient.webservice.getTragoByName(tragoName).drinkList)
    }
}