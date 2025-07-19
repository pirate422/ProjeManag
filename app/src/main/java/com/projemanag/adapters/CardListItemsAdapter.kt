package com.projemanag.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projemanag.activities.TaskListActivity
import com.projemanag.databinding.ItemCardBinding
import com.projemanag.model.Card
import com.projemanag.model.SelectedMembers

open class CardListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Card>
) : RecyclerView.Adapter<CardListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    /**
     * Inflates the item views which is designed in xml layout file
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     */
    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val model = list[position]

        // Show and set the label color if available
        if (model.labelColor.isNotEmpty()) {
            holder.binding.viewLabelColor.visibility = android.view.View.VISIBLE
            holder.binding.viewLabelColor.setBackgroundColor(Color.parseColor(model.labelColor))
        } else {
            holder.binding.viewLabelColor.visibility = android.view.View.GONE
        }

        holder.binding.tvCardName.text = model.name

        if ((context as TaskListActivity).mAssignedMembersDetailList.size > 0) {
            // A instance of selected members list.
            val selectedMembersList: ArrayList<SelectedMembers> = ArrayList()

            // Here we got the detail list of members and add it to the selected members list as required.
            for (i in context.mAssignedMembersDetailList.indices) {
                for (j in model.assignedTo) {
                    if (context.mAssignedMembersDetailList[i].id == j) {
                        val selectedMember = SelectedMembers(
                            context.mAssignedMembersDetailList[i].id,
                            context.mAssignedMembersDetailList[i].image
                        )

                        selectedMembersList.add(selectedMember)
                    }
                }
            }

            if (selectedMembersList.size > 0) {

                if (selectedMembersList.size == 1 && selectedMembersList[0].id == model.createdBy) {
                    holder.binding.rvCardSelectedMembersList.visibility = View.GONE
                } else {
                    holder.binding.rvCardSelectedMembersList.visibility = View.VISIBLE

                    holder.binding.rvCardSelectedMembersList.layoutManager =
                        GridLayoutManager(context, 4)
                    val adapter = CardMemberListItemsAdapter(context, selectedMembersList, false)
                    holder.binding.rvCardSelectedMembersList.adapter = adapter
                    adapter.setOnClickListener(object :
                        CardMemberListItemsAdapter.OnClickListener {
                        override fun onClick() {
                            if (onClickListener != null) {
                                onClickListener!!.onClick(position)
                            }
                        }
                    })
                }
            } else {
                holder.binding.rvCardSelectedMembersList.visibility = View.GONE
            }
        }

        holder.binding.root.setOnClickListener {
            onClickListener?.onClick(position)
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
        fun onClick(cardPosition: Int)
    }

    /**
     * ViewHolder class using ViewBinding
     */
    class MyViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}
