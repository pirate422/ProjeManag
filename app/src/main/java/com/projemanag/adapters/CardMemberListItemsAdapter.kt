package com.projemanag.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.projemanag.R
import com.projemanag.databinding.ItemCardSelectedMemberBinding
import com.projemanag.model.SelectedMembers

// TODO (Step 2: Create an adapter class for selected members list.)
open class CardMemberListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<SelectedMembers>,
    private val assignMembers: Boolean
) : RecyclerView.Adapter<CardMemberListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    /**
     * Inflates the item views which is designed in XML layout file using ViewBinding.
     *
     * Creates a new {@link ViewHolder} and initializes ViewBinding properties.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardSelectedMemberBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view using the binding reference.
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item. You can either create a new View manually or inflate it from an XML layout file.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        // If it's the last item, show the "Add Member" icon
        if (position == list.size - 1 && assignMembers) {
            holder.binding.ivAddMember.visibility = android.view.View.VISIBLE
            holder.binding.ivSelectedMemberImage.visibility = android.view.View.GONE
        } else {
            // Otherwise, show the member's image
            holder.binding.ivAddMember.visibility = android.view.View.GONE
            holder.binding.ivSelectedMemberImage.visibility = android.view.View.VISIBLE

            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(holder.binding.ivSelectedMemberImage)
        }

        // Handle click for each item
        holder.binding.root.setOnClickListener {
            onClickListener?.onClick()
        }
    }

    /**
     * Gets the number of items in the list.
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A function for setting the OnClickListener where the interface is the expected parameter.
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    /**
     * An interface for onclick items.
     */
    interface OnClickListener {
        fun onClick()
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     *
     * Now uses ViewBinding to access views instead of synthetic properties.
     */
    class MyViewHolder(val binding: ItemCardSelectedMemberBinding) :
        RecyclerView.ViewHolder(binding.root)
}
// END
