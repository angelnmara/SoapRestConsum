package com.example.redpacktest.ui.codigopostal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.redpacktest.R
import com.example.redpacktest.api.Repo
import com.example.redpacktest.tools.Resource
import kotlinx.coroutines.launch

class CodigoPostalViewModel(private val repo: Repo):ViewModel() {

    private val TAG = javaClass.name

    private val _codigoPostalResult = MutableLiveData<CodigoPostalResult>()
    val codigoPostalResult:LiveData<CodigoPostalResult> = _codigoPostalResult

    fun getCodigoPostal(codigo:Int){
        viewModelScope.launch {
            val result = repo.getColoniasByCodigo(codigo)
            if(result is Resource.Success){
                if(result.data!=null){
                    _codigoPostalResult.value = CodigoPostalResult(success = CodigoPostalInUserView(result.data))
                }else{
                    _codigoPostalResult.value = CodigoPostalResult(error = R.string.ninguna_coincidencia)
                }
            }else{
                _codigoPostalResult.value = CodigoPostalResult(error = R.string.error_codigo_postal)
            }
        }
    }
}