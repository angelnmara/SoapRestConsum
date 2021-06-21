package com.example.redpacktest.data.model

import com.google.gson.annotations.SerializedName

data class GeoCodeRequest(
    val guia:String = "",
    val consignatario:String = "",
    val calle:String = "",
    val colonia:String = "",
    val municipio:String = "",
    val estado:String = "",
    val postal:String = "",
    val extra:String = ""
)
