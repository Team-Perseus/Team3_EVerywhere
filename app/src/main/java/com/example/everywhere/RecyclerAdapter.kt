package com.example.everywhere


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.everywhere.models.Document
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerAdapter constructor(private var items : ArrayList<Document>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, data: Document)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                tvPlaceName.text = item.placeName
                tvAddressName.text = item.addressName
            }
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, items[position])
        }
    }

    fun setOnItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun addItem(dataList: List<Document>) {
        items.addAll(dataList)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvPlaceName = itemView.tv_item_placename
        val tvAddressName = itemView.tv_item_addressname
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }
}