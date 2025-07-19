package com.projemanag.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.projemanag.adapters.LabelColorListItemsAdapter
import com.projemanag.databinding.DialogListBinding

/**
 * A custom dialog for displaying a list of label colors.
 */
abstract class LabelColorListDialog(
    context: Context,
    private var list: ArrayList<String>,
    private val title: String = "",
    private val mSelectedColor: String = ""
) : Dialog(context) {

    private lateinit var binding: DialogListBinding
    private var adapter: LabelColorListItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = DialogListBinding.inflate(LayoutInflater
            .from(context))
        setContentView(binding.root)

        setCanceledOnTouchOutside(true)
        setCancelable(true)

        setUpRecyclerView()
    }

    /**
     * Sets up the RecyclerView with the label color adapter.
     */
    private fun setUpRecyclerView() {
        binding.tvTitle.text = title
        binding.rvList.layoutManager = LinearLayoutManager(context)

        adapter = LabelColorListItemsAdapter(context, list, mSelectedColor)
        binding.rvList.adapter = adapter

        adapter?.onItemClickListener = object : LabelColorListItemsAdapter.OnItemClickListener {
            override fun onClick(position: Int, color: String) {
                dismiss()
                onItemSelected(color)
            }
        }
    }

    /**
     * Abstract function to handle selected color.
     */
    protected abstract fun onItemSelected(color: String)
}
