package com.example.redpacktest.api

import com.example.redpacktest.data.model.DataSource
import com.example.redpacktest.data.model.Drink
import com.example.redpacktest.data.model.GeoCode
import com.example.redpacktest.data.model.GeoCodeRequest
import com.example.redpacktest.tools.Resource
import okhttp3.RequestBody

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getGeoCode(geoCodeRequest: GeoCodeRequest): Resource<GeoCode> {
        return dataSource.getGeoCode(geoCodeRequest)
    }
    override suspend fun getTrago(tragoName:String):Resource<List<Drink>>{
        return dataSource.getTrago(tragoName)
    }

    override suspend fun getColoniasByCodigo(codigo: Int): Resource<List<String>> {
        return dataSource.getColoniasByCodigo(codigo)
    }
}