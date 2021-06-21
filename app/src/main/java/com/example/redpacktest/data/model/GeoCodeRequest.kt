package com.example.redpacktest.data.model

import com.google.gson.annotations.SerializedName

data class GeoCodeRequest(
    @SerializedName("guia")
    val guia:String = "",
    @SerializedName("consignatario")
    val consignatario:String = "",
    @SerializedName("calle")
    val calle:String = "",
    @SerializedName("colonia")
    val colonia:String = "",
    @SerializedName("municipio")
    val municipio:String = "",
    @SerializedName("estado")
    val estado:String = "",
    @SerializedName("postal")
    val postal:String = "",
    @SerializedName("extra")
    val extra:String = ""
)
