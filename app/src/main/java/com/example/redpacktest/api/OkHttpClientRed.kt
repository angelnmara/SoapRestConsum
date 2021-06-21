package com.example.redpacktest.api

import android.util.Log
import com.example.redpacktest.tools.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import okhttp3.RequestBody

import okhttp3.MediaType

import okhttp3.OkHttpClient
import org.json.JSONObject


class OkHttpClientRed {

    lateinit var client: OkHttpClient
    lateinit var request: Request
    val TAG = javaClass.name

    public suspend fun httpPrueba(codigo:Int): Resource<List<String>> {
        client = OkHttpClient().newBuilder()
            .build()
        val mediaType = "application/xml".toMediaTypeOrNull()
        val body = RequestBody.create(
            mediaType,
            "\n\n\t\n\t\t<busquedaCodigoPostal xmlns=\"http://ws.redpack.com\">\n\t\t\t<PIN>QA BpjVbgxpoCIbhP1a+kRsPhb238k7zlAI</PIN>\n\t\t\t<idUsuario>297</idUsuario>\n\t\t\t<guias>\n\t\t\t\t<ns1:flag xmlns:ns1=\"http://vo.redpack.com/xsd\">0</ns1:flag>\n\t\t\t\t<ns2:remitente xmlns:ns2=\"http://vo.redpack.com/xsd\">\n\t\t\t\t\t<ns2:codigoPostal>" + codigo.toString() +  "</ns2:codigoPostal>\n\t\t\t\t</ns2:remitente>\n\t\t\t</guias>\n\t\t</busquedaCodigoPostal>\n\t\n    "
        )
        request = Request.Builder()
            .url("http://ws.redpack.com.mx/RedpackAPI_WS/services/RedpackWS?wsdl")
            .method("POST", body)
            .addHeader("Content-Type", "application/xml")
            .addHeader(
                "Cookie",
                "JSESSIONID=500a3dac5cbe61723a34f5810d77; AWSALB=OVxxwFlkNciubpROwJdUh5HqpEoY9Ted5E1AyxQCNBFy+BthPgahneL+Yn9sCKHSv/4hUtk8R7yBCddgJlBbfLYwBXv3/8Z+WFoJM7W5Pjs48sFxkQyZH4T5nGWG; AWSALBCORS=OVxxwFlkNciubpROwJdUh5HqpEoY9Ted5E1AyxQCNBFy+BthPgahneL+Yn9sCKHSv/4hUtk8R7yBCddgJlBbfLYwBXv3/8Z+WFoJM7W5Pjs48sFxkQyZH4T5nGWG"
            )
            .build()
        //executeRequest()
        return executeRequest2().getCompleted()
    }

    private suspend fun executeRequest2(): Deferred<Resource<List<String>>> = withContext(
        Dispatchers.IO) {
        async {
            suspendCoroutine<Resource<List<String>>> {
                    continuation ->
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d(TAG, "onFailure: " + e.toString())
                        continuation.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string().toString()
                        var listString = llenalista(body)
                        continuation.resume(Resource.Success(listString))
                    }
                })
            }
        }
    }

    private fun llenalista(body:String): List<String>{
        var listString = arrayListOf<String>()
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        var d1  = builder.parse(InputSource(StringReader(body)))

        val nlist = d1.getElementsByTagName("ax21:descripcion")
        for (i in 0 until nlist.length){
            if(nlist.item(i).parentNode.nodeName.toString().equals("ax21:auxiliar")){
                Log.d(TAG, "executeRequest: " + nlist.item(i).childNodes.item(0).nodeValue.toString())
                listString.add(nlist.item(i).childNodes.item(0).nodeValue.toString())
            }
        }
        return listString
    }

    public suspend fun geocodeResponse(geoCodeRequest: String): String{
        client = OkHttpClient().newBuilder()
            .build()
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(
            mediaType,
            geoCodeRequest
        )
        request = Request.Builder()
            .url("https://geoloc.redpack.com.mx/geocode")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build()
        return responseString().getCompleted()
    }
    private suspend fun responseString(): Deferred<String> = withContext(
        Dispatchers.IO) {
        async {
            suspendCoroutine<String> {
                    continuation ->
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d(TAG, "onFailure: " + e.toString())
                        continuation.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val body = response.body?.string().toString()
                        val bodyJsonOK = body.replace("\"{", "{").replace("\\", """""").replace("}\"", "}")
                        //val jsoBody = JSONObject(bodyJsonOK)
                        //var listString = llenalista(body)
                        continuation.resume(bodyJsonOK)
                    }
                })
            }
        }
    }
}