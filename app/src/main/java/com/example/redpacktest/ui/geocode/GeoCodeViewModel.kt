package com.example.redpacktest.ui.geocode

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpacktest.R
import com.example.redpacktest.api.Repo
import com.example.redpacktest.data.model.GeoCodeRequest
import com.example.redpacktest.tools.Resource
import com.example.redpacktest.ui.geocode.placeholder.GeoCodeResult
import kotlinx.coroutines.launch

class GeoCodeViewModel(private val repo: Repo):ViewModel() {

    private val TAG = javaClass.name

    private val _geoCodeResult = MutableLiveData<GeoCodeResult>()
    val geoCodeResult:LiveData<GeoCodeResult> = _geoCodeResult

    fun BuscaGeoCode(geoCodeRequest: String){
        viewModelScope.launch {
            try{
                val result = repo.getGeoCode(geoCodeRequest)
                if(result is Resource.Success){
                    Log.d(TAG, "BuscaGeoCode: " + result.data)
                    _geoCodeResult.value = GeoCodeResult(success = GeoCodeInUserView(result.data))
                }else{
                    Log.d(TAG, "BuscaGeoCode: error")
                    _geoCodeResult.value = GeoCodeResult(error = R.string.error_geocode)
                }
            }catch (e:Exception){
                Log.d(TAG, "BuscaGeoCode: " + e.message)
                _geoCodeResult.value = GeoCodeResult(error = R.string.error_geocode)
            }

        }
    }
}