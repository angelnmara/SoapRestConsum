package com.example.redpacktest.api

import com.example.redpacktest.data.model.*
import com.example.redpacktest.tools.Resource

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getGeoCode(geoCodeRequest: String): Resource<GeoCodeResponse> {
        return dataSource.getGeoCodeHttp(geoCodeRequest)
    }
    override suspend fun getTrago(tragoName:String):Resource<List<Drink>>{
        return dataSource.getTrago(tragoName)
    }

    override suspend fun getColoniasByCodigo(codigo: Int): Resource<List<String>> {
        return dataSource.getColoniasByCodigo(codigo)
    }
}