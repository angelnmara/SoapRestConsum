package com.example.redpacktest.ui.trago

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpacktest.R
import com.example.redpacktest.api.Repo
import com.example.redpacktest.tools.Resource
import kotlinx.coroutines.launch


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
                    _tragosResult.value = TragosResult(error = R.string.ninguna_coincidencia_tragos)
                }
            }else{
                Log.d(TAG, "getTragos: no se obtuvo información en tragos")
                _tragosResult.value = TragosResult(error = R.string.error_tragos)
            }
        }
    }
}