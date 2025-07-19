package com.projemanag.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.projemanag.databinding.ItemLabelColorBinding

class LabelColorListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<String>,
    private val mSelectedColor: String
) : RecyclerView.Adapter<LabelColorListItemsAdapter.MyViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLabelColorBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]

        // Set background color of main view to the label color
        holder.binding.viewMain.setBackgroundColor(Color.parseColor(item))

        // Show or hide the selected tick icon
        if (item == mSelectedColor) {
            holder.binding.ivSelectedColor.visibility = android.view.View.VISIBLE
        } else {
            holder.binding.ivSelectedColor.visibility = android.view.View.GONE
        }

        // Handle click listener for color selection
        holder.binding.root.setOnClickListener {
            onItemClickListener?.onClick(position, item)
        }
    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(val binding: ItemLabelColorBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickListener {
        fun onClick(position: Int, color: String)
    }
}
