package com.example.redpacktest.ui.codigopostal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.redpacktest.R
import com.example.redpacktest.api.RepoImpl
import com.example.redpacktest.data.model.DataSource
import com.example.redpacktest.tools.VMFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a list of Items.
 */
class CodigoPostalFragment : Fragment(), SearchView.OnQueryTextListener {

    private val TAG = javaClass.name
    private lateinit var rvCodigoPostal:RecyclerView
    private lateinit var rlProgresBarCodigoPostal:RelativeLayout
    private val viewModel by viewModels<CodigoPostalViewModel> {
        VMFactory(RepoImpl(DataSource()))
    }
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    private fun setupObserver(rvCodigoPostal:RecyclerView){
        viewModel.getCodigoPostal(0)
        viewModel.codigoPostalResult.observe(viewLifecycleOwner, Observer {
            codigoPostalResult->
            codigoPostalResult?:return@Observer
            rlProgresBarCodigoPostal.visibility = View.VISIBLE
            codigoPostalResult.error?.let{
                error->
                Log.d(TAG, "setupObserver: " + error)
                Snackbar.make(rvCodigoPostal, getString(error), Snackbar.LENGTH_SHORT).show()
                rlProgresBarCodigoPostal.visibility = View.GONE
            }
            codigoPostalResult.success?.let{
                success->
                Log.d(TAG, "setupObserver: " + success.listaCodigoPostal.toString())
                if(success.listaCodigoPostal.isEmpty()){
                    Snackbar.make(rvCodigoPostal, getString(R.string.error_codigo_postal), Snackbar.LENGTH_SHORT).show()
                }
                rvCodigoPostal.adapter = MyCodigoPostalRecyclerViewAdapter(success.listaCodigoPostal)
                rlProgresBarCodigoPostal.visibility = View.GONE
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_codigo_postal_list, container, false)
        rvCodigoPostal = view.findViewById(R.id.listCodigoPostal)
        rlProgresBarCodigoPostal = view.findViewById(R.id.progress_bar_codigo_postal)
        // Set the adapter
        if (rvCodigoPostal is RecyclerView) {
            with(rvCodigoPostal) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                //adapter = MyCodigoPostalRecyclerViewAdapter(PlaceholderContent.ITEMS)
                setupObserver(rvCodigoPostal)
            }
        }
        setupSearchView(view)
        return view
    }

    private fun setupSearchView(view: View?) {
        val searchViewCodigoPostal = view?.findViewById<SearchView>(R.id.srv_codigo_postal)
        searchViewCodigoPostal?.setOnQueryTextListener(this)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CodigoPostalFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "onQueryTextSubmit: " + query)
        viewModel.getCodigoPostal(query?.toInt()!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "onQueryTextChange: " + newText)
        if(newText?.length==0){
            viewModel.getCodigoPostal(0)
        }
        return true
    }
}