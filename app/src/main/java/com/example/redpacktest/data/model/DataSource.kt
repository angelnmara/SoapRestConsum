package com.example.redpacktest.data.model

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.redpacktest.api.OkHttpClientRed
import com.example.redpacktest.api.RetrofitClient
import com.example.redpacktest.tools.Resource
import kotlinx.coroutines.*
import okhttp3.*
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.log

class DataSource {
    suspend fun getGeoCode():Resource<List<GeoCode>>{
        return Resource.Success(RetrofitClient.webservice.getGeocode())
    }
    suspend fun getTrago(tragoName:String):Resource<List<Drink>>{
        return Resource.Success(RetrofitClient.webservice.getTragoByName(tragoName).drinkList)
    }
    suspend fun getColoniasByCodigo(codigo:Int):Resource<List<String>>{
        val okHttpClientRed = OkHttpClientRed()
        return okHttpClientRed.httpPrueba(codigo);
    }
}