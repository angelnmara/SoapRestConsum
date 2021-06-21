package com.example.redpacktest.ui.trago

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpacktest.R
import com.example.redpacktest.api.Repo
import com.example.redpacktest.tools.Resource
import okhttp3.OkHttpClient
import okhttp3.Request
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import kotlinx.coroutines.*
import okhttp3.RequestBody

import okhttp3.MediaType
import java.io.StringReader

import org.xml.sax.InputSource

import javax.xml.parsers.DocumentBuilder

import javax.xml.parsers.DocumentBuilderFactory

class TragoViewModel(private val repo:Repo):ViewModel() {

    val TAG = javaClass.name

    private val _tragosResult = MutableLiveData<TragosResult>()
    val tragosResult:LiveData<TragosResult> = _tragosResult

    fun setTragoName(tragoName: String){
        getTragos(tragoName)
    }

    fun getTragos(tragoName:String){
        viewModelScope.launch {
            val result = repo.getTrago(tragoName)
            if(result is Resource.Success){
                Log.d(TAG, "getTragos: " + result.data.toString())
                if(result.data != null){
                    _tragosResult.value = TragosResult(success = TragosInUserView(result.data))
                }
                else{
                    _tragosResult.value = TragosResult(error = R.string.ninguna_coincidencia)
                }
            }else{
                Log.d(TAG, "getTragos: no se obtuvo información en tragos")
                _tragosResult.value = TragosResult(error = R.string.error_tragos)
            }
        }
    }
}