package com.garageKoi.garage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.garageKoi.garage.databinding.RowGarageTypeBinding

class GarageTypeListAdapter(
    private val context: Context,
    private var dataList: List<String>
) : RecyclerView.Adapter<GarageTypeListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowGarageTypeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataList[position]

        holder.binding.apply {
            // name
            txtTitle.text = data
        }


    }

    inner class ViewHolder(val binding: RowGarageTypeBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}