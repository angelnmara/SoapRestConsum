package com.example.redpacktest.ui.trago

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.redpacktest.R
import com.example.redpacktest.data.model.Drink
import com.example.redpacktest.databinding.FragmentTragoBinding

import com.example.redpacktest.ui.trago.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyTragoRecyclerViewAdapter(
    private val values: List<Drink>
) : RecyclerView.Adapter<MyTragoRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTragoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.nombre
        holder.contentView.text = item.descripcion
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentTragoBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}