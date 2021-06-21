package com.example.redpacktest.ui.geocode

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.example.redpacktest.R
import com.example.redpacktest.api.RepoImpl
import com.example.redpacktest.data.model.DataSource
import com.example.redpacktest.data.model.GeoCodeRequest
import com.example.redpacktest.tools.VMFactory
import com.example.redpacktest.ui.geocode.placeholder.PlaceholderContent
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
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
    private lateinit var btnLimpiar:Button
    private lateinit var etGuia: EditText
    private lateinit var etConsignatario:EditText
    private lateinit var etCalle:EditText
    private lateinit var etColonia:EditText
    private lateinit var etMunicipio:EditText
    private lateinit var etEstado:EditText
    private lateinit var etPostal:EditText
    private lateinit var etExtra:EditText
    private lateinit var txvResponse:TextView
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
        btnLimpiar = view.findViewById(R.id.btnLimpiar)
        btnLimpiar.setOnClickListener(this)
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
        txvResponse = view.findViewById(R.id.txvResponse)
        viewModel.geoCodeResult.observe(viewLifecycleOwner, Observer {
            geoCodeResult->
            geoCodeResult?:return@Observer
            geoCodeResult.error?.let {
                error->
                Log.d(TAG, "configTexboxes: " + error)
                Snackbar.make(view, getString(error), Snackbar.LENGTH_SHORT).show()
                txvResponse.text = ""
            }
            geoCodeResult.success?.let {
                success->
                Log.d(TAG, "configTexboxes: " + success.geoCodeInUserView.data)
                txvResponse.text = success.geoCodeInUserView.data.suggested
            }
        })
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
        when(v?.id){
            R.id.btnBuscar->{
                ejecutaBuscar()
            }
            R.id.btnLimpiar->{
                ejecutaLimpiar()
            }
            else->{
                Toast.makeText(requireContext(), "Opcion invalida", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ejecutaLimpiar() {
        etPostal.setText("")
        etMunicipio.setText("")
        etEstado.setText("")
        etExtra.setText("")
        etConsignatario.setText("")
        etColonia.setText("")
        etCalle.setText("")
        etGuia.setText("")
    }

    private fun ejecutaBuscar(){
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

        val gson = Gson()
        val jso = gson.toJson(geoCodeRequest)

        viewModel.BuscaGeoCode(jso)
    }
}