package com.projemanag.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projemanag.databinding.ItemBoardBinding
import com.projemanag.model.Board

open class BoardItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Board>
) : RecyclerView.Adapter<BoardItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    /**
     * Inflates the item views which is designed in xml layout file
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBoardBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        Glide
            .with(context)
            .load(model.image)
            .centerCrop()
            .placeholder(com.projemanag.R.drawable.ic_board_place_holder)
            .into(holder.binding.ivBoardImage)

        holder.binding.tvName.text = model.name
        holder.binding.tvCreatedBy.text = "Created By : ${model.createdBy}"

        holder.binding.root.setOnClickListener {
            onClickListener?.onClick(position, model)
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int = list.size

    /**
     * A function for setting OnClickListener.
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    /**
     * An interface for onclick items.
     */
    interface OnClickListener {
        fun onClick(position: Int, model: Board)
    }

    /**
     * ViewHolder class using ViewBinding
     */
    class MyViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root)
}
