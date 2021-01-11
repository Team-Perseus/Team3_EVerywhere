package com.example.everywhere


import com.example.everywhere.models.Document

interface SearchLocalConstants {
    interface View {
        fun renewLocalList(localList: List<Document>)
        fun notifyDataSetChanged()
        fun showError(error: String)
    }

    interface Presenter {
        fun callLocalList(name: String)
        fun getLocalArrList(): ArrayList<Document>
        fun onClear()
        fun reset()
        fun searchMore()
        fun destroyView()
    }
}