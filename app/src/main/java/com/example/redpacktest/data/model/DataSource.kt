package com.example.redpacktest.data.model

import com.beust.klaxon.Klaxon
import com.example.redpacktest.api.OkHttpClientRed
import com.example.redpacktest.api.RetrofitClient
import com.example.redpacktest.tools.Resource
import com.google.gson.Gson
import retrofit2.Response
import java.lang.Exception

class DataSource {
    suspend fun getGeoCode(geoCodeRequest: GeoCodeRequest):Resource<GeoCodeResponse>{
        val cadena = "{\"guia\": \"\", \"consignatario\": \"\", \"calle\": \"claveria\", \"colonia\": \"\", \"municipio\": \"nextlalpan\", \"estado\": \"\", \"postal\":\"\",\"extra\": \"\"}"
        val response = RetrofitClient.webserviceGeo.getGeocode(cadena)
        lateinit var geoCodeResponse:GeoCodeResponse
        return Resource.Success(geoCodeResponse)
    }
    suspend fun getTrago(tragoName:String):Resource<List<Drink>>{
        return Resource.Success(RetrofitClient.webserviceDrinks.getTragoByName(tragoName).drinkList)
    }
    suspend fun getColoniasByCodigo(codigo:Int):Resource<List<String>>{
        val okHttpClientRed = OkHttpClientRed()
        return okHttpClientRed.httpPrueba(codigo);
    }
    suspend fun getGeoCodeHttp(geoCodeRequest: String):Resource<GeoCodeResponse>{
        val okHttpClientRed = OkHttpClientRed()
        var stringRes =  okHttpClientRed.geocodeResponse(geoCodeRequest);
        /*val result = Klaxon()
            .parse<GeoCodeResponse>(stringRes)*/
        val gson = Gson()
        val result = gson.fromJson(stringRes, GeoCodeResponse::class.java)
        var geoCodeResponse = result as GeoCodeResponse
        if(geoCodeResponse.statusCode==200){
            return Resource.Success(geoCodeResponse)
        }else{
            return Resource.Failure(Exception("No se pudo obtener la informacion"))
        }

    }
}