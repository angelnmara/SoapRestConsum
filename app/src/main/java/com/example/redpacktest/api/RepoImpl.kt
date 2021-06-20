package com.example.redpacktest.api

import com.example.redpacktest.data.model.DataSource
import com.example.redpacktest.data.model.Drink
import com.example.redpacktest.data.model.GeoCode
import com.example.redpacktest.tools.Resource

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getGeoCode(): Resource<List<GeoCode>> {
        return dataSource.getGeoCode()
    }
    override suspend fun getTrago(tragoName:String):Resource<List<Drink>>{
        return dataSource.getTrago(tragoName)
    }
}