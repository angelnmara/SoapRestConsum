package com.example.redpacktest.ui.geocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import androidx.fragment.app.viewModels

import com.example.redpacktest.R
import com.example.redpacktest.api.RepoImpl
import com.example.redpacktest.data.model.DataSource
import com.example.redpacktest.data.model.GeoCodeRequest
import com.example.redpacktest.tools.VMFactory
import com.example.redpacktest.ui.geocode.placeholder.PlaceholderContent
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

import org.json.JSONObject

/**
 * A fragment representing a list of Items.
 */
class GeoCodeFragment : Fragment(), View.OnClickListener {

    private var columnCount = 1
    private val TAG = javaClass.name
    private lateinit var btnBuscar:Button
    private lateinit var etGuia: EditText
    private lateinit var etConsignatario:EditText
    private lateinit var etCalle:EditText
    private lateinit var etColonia:EditText
    private lateinit var etMunicipio:EditText
    private lateinit var etEstado:EditText
    private lateinit var etPostal:EditText
    private lateinit var etExtra:EditText
    private val viewModel by viewModels<GeoCodeViewModel>{
        VMFactory(RepoImpl(DataSource()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_geocode_list, container, false)
        val rvGeoCode = view.findViewById<RecyclerView>(R.id.listGeoCode)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        btnBuscar.setOnClickListener(this)
        configTexboxes(view)
        // Set the adapter
        if (rvGeoCode is RecyclerView) {
            with(rvGeoCode) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyGeoCodeRecyclerViewAdapter(PlaceholderContent.ITEMS)
            }
        }
        return view
    }

    private fun configTexboxes(view: View) {
        etGuia = view.findViewById(R.id.etGuia)
        etCalle = view.findViewById(R.id.etCalle)
        etColonia = view.findViewById(R.id.etColonia)
        etConsignatario = view.findViewById(R.id.etConsignatario)
        etEstado = view.findViewById(R.id.etEstado)
        etExtra = view.findViewById(R.id.etExtra)
        etMunicipio = view.findViewById(R.id.etMunicipio)
        etPostal = view.findViewById(R.id.etPostal)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            GeoCodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onClick(v: View?) {
        val geoCodeRequest = GeoCodeRequest(
            guia = etGuia.text.toString(),
            consignatario = etConsignatario.text.toString(),
            calle = etCalle.text.toString(),
            colonia = etColonia.text.toString(),
            municipio = etMunicipio.text.toString(),
            estado = etEstado.text.toString(),
            postal = etPostal.text.toString(),
            extra = etExtra.text.toString()
        )

        val jsonObject = JSONObject()
        jsonObject.put("calle", "Sierra dorada")
        jsonObject.put("municipio", "coacalco")

        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        viewModel.BuscaGeoCode(geoCodeRequest)
    }
}