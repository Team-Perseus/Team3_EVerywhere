package com.example.everywhere


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.everywhere.models.Document
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerAdapter constructor(private var items : ArrayList<Document>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, data: Document)
    }

    //전체 데이터 개수 리턴
    override fun getItemCount(): Int = items.size

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
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
        /* holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView?.context, BookmarkActivity::class.java)
            intent.putExtra("content", "ImageView")
            intent.putExtra("no", 111)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        } */
    }

    fun setOnItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun addItem(dataList: List<Document>) {
        items.addAll(dataList)
    }

    //아이템 뷰를 저장하는 뷰홀터 클래스
    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvPlaceName = itemView.tv_item_placename
        val tvAddressName = itemView.tv_item_addressname
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }
}