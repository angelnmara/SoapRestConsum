package com.example.redpacktest.ui.geocode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpacktest.api.Repo
import com.example.redpacktest.data.model.GeoCodeRequest
import com.example.redpacktest.tools.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class GeoCodeViewModel(private val repo: Repo):ViewModel() {

    private val TAG = javaClass.name

    fun BuscaGeoCode(geoCodeRequest: GeoCodeRequest){
        viewModelScope.launch {
            try{
                val result = repo.getGeoCode(geoCodeRequest)
                if(result is Resource.Success){
                    Log.d(TAG, "BuscaGeoCode: " + result.data)
                }else{
                    Log.d(TAG, "BuscaGeoCode: error")
                }
            }catch (e:Exception){
                Log.d(TAG, "BuscaGeoCode: " + e.message)
            }

        }
    }
}