package com.example.redpacktest.api

import com.example.redpacktest.data.model.Drink
import com.example.redpacktest.data.model.GeoCode
import com.example.redpacktest.data.model.GeoCodeRequest
import com.example.redpacktest.data.model.GeoCodeResponse
import com.example.redpacktest.tools.Resource

interface Repo {
    suspend fun getGeoCode(geoCodeRequest: String): Resource<GeoCodeResponse>
    suspend fun getTrago(tragoName:String): Resource<List<Drink>>
    suspend fun getColoniasByCodigo(codigo:Int): Resource<List<String>>
}