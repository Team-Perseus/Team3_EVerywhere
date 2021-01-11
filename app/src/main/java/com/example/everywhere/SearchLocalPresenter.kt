package com.example.everywhere


import com.example.everywhere.api.RetrofitAPI
import com.example.everywhere.models.Document
import com.example.everywhere.models.Items
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SearchLocalPresenter(var searchLocalView: SearchLocalConstants.View?) :
    SearchLocalConstants.Presenter {

    var dataList = ArrayList<Document>()
    var pageCounter = 1
    var isEnd = false
    var keyword: String = ""

    override fun callLocalList(name: String) {
        if (name.equals("")) {
            searchLocalView!!.showError("정확히 입력해주세요")
            return
        }
        RetrofitAPI.getService().requestSearchLocal(keyword = name, page = pageCounter)
            .enqueue(object : Callback<Items> {
                override fun onResponse(call: Call<Items>, response: Response<Items>) {
                    if (response.isSuccessful) {
                        keyword = name
                        val items = response.body()
                        isEnd = items!!.isEnd()
                        searchLocalView?.renewLocalList(items!!.documents)
                    } else {
                        response.errorBody()?.string()?.let { searchLocalView!!.showError(it) }
                    }
                }

                override fun onFailure(call: Call<Items>, t: Throwable) {
                    t.stackTrace
                    Timber.e(t)
                }
            })
    }

    override fun getLocalArrList(): ArrayList<Document> {
        return dataList
    }

    override fun onClear() {
        dataList.clear()
        searchLocalView!!.notifyDataSetChanged()
    }

    override fun reset() {
        pageCounter = 1
        isEnd = false
        keyword = ""
        onClear()
    }

    override fun searchMore() {
        pageCounter++
        if (!keyword.equals("") && !isEnd) {
            callLocalList(keyword)
        }
    }

    override fun destroyView() {
        searchLocalView = null
    }
}