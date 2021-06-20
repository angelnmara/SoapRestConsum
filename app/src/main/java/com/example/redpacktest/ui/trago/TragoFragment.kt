package com.example.redpacktest.ui.trago

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.redpacktest.R
import com.example.redpacktest.api.RepoImpl
import com.example.redpacktest.tools.VMFactory
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a list of Items.
 */
class TragoFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private val TAG = javaClass.name
    private val viewModel by viewModels<TragoViewModel>{VMFactory(RepoImpl(com.example.redpacktest.data.model.DataSource()))}
    private var columnCount = 1
    private lateinit var rvProgresBar: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    private fun setupObserver(rvTragos:RecyclerView){
        viewModel.getTragos("")
        viewModel.tragosResult.observe(viewLifecycleOwner, Observer {
                tragosResult ->
            tragosResult ?: return@Observer
            rvProgresBar.visibility = View.VISIBLE
            tragosResult.error?.let {
                    error ->
                Log.d(TAG, "onCreate: " + error)
                Snackbar.make(rvTragos, getString(error), Snackbar.LENGTH_SHORT).show()
                rvProgresBar.visibility = View.GONE
            }
            tragosResult.success?.let {
                    success->
                Log.d(TAG, "onCreate: " + success.listaTragos.toString())
                rvTragos.adapter = MyTragoRecyclerViewAdapter(success.listaTragos)
                rvProgresBar.visibility = View.GONE
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_trago_list, container, false)
        val rvTragos = view.findViewById<RecyclerView>(R.id.listTragos)
        rvProgresBar = view.findViewById(R.id.progress_bar_trago)
        // Set the adapter
        if (rvTragos is RecyclerView) {
            with(rvTragos) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                setupObserver(rvTragos);
            }
        }
        setupSearchView(view)
        return view
    }

    private fun setupSearchView(view: View?) {
        val searchViewTrago = view?.findViewById<androidx.appcompat.widget.SearchView>(R.id.srv_trago)
        searchViewTrago?.setOnQueryTextListener(this)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TragoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "onQueryTextSubmit: ")
        viewModel.setTragoName(query.toString())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "onQueryTextChange: ")
        if(newText?.length == 0){
            viewModel.setTragoName(newText)
        }
        return true
    }
}