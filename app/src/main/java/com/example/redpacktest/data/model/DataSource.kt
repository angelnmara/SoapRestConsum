package com.example.redpacktest.data.model

import com.example.redpacktest.api.OkHttpClientRed
import com.example.redpacktest.api.RetrofitClient
import com.example.redpacktest.tools.Resource
import okhttp3.RequestBody

class DataSource {
    suspend fun getGeoCode(geoCodeRequest: GeoCodeRequest):Resource<GeoCode>{
        val cadena = "{\"guia\": \"\", \"consignatario\": \"\", \"calle\": \"claveria\", \"colonia\": \"\", \"municipio\": \"nextlalpan\", \"estado\": \"\", \"postal\":\"\",\"extra\": \"\"}"
        return Resource.Success(RetrofitClient.webserviceGeo.getGeocode(cadena))
    }
    suspend fun getTrago(tragoName:String):Resource<List<Drink>>{
        return Resource.Success(RetrofitClient.webserviceDrinks.getTragoByName(tragoName).drinkList)
    }
    suspend fun getColoniasByCodigo(codigo:Int):Resource<List<String>>{
        val okHttpClientRed = OkHttpClientRed()
        return okHttpClientRed.httpPrueba(codigo);
    }
}