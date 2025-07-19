package com.projemanag.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projemanag.R
import com.projemanag.databinding.ItemMemberBinding
import com.projemanag.model.User
import com.projemanag.utils.Constants

open class MemberListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<User>
) : RecyclerView.Adapter<MemberListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    /**
     * Inflates the item views which is designed in xml layout file
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        Glide.with(context)
            .load(model.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(holder.binding.ivMemberImage)

        holder.binding.tvMemberName.text = model.name
        holder.binding.tvMemberEmail.text = model.email

        if (model.selected) {
            holder.binding.ivSelectedMember.visibility = View.VISIBLE
        } else {
            holder.binding.ivSelectedMember.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {

            if (onClickListener != null) {
                // TODO (Step 3: Pass the constants here according to the selection.)
                // START
                if (model.selected) {
                    onClickListener!!.onClick(position, model, Constants.UN_SELECT)
                } else {
                    onClickListener!!.onClick(position, model, Constants.SELECT)
                }
                // END
            }
        }
    }


    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int = list.size

    /**
     * A function for OnClickListener where the Interface is the expected parameter.
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    /**
     * An interface for onclick items.
     */
    interface OnClickListener {
        fun onClick(position: Int, user: User, action: String)
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class MyViewHolder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root)
}
