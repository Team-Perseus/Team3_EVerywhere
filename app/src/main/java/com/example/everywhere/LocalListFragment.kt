package com.example.everywhere


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everywhere.models.Document
import kotlinx.android.synthetic.main.fragment_local_list.view.*

class LocalListFragment : Fragment() {
    lateinit var fragmentView: View

    lateinit var presenter: SearchLocalConstants.Presenter
    lateinit var localAdapter: RecyclerAdapter
    lateinit var endlessRecyclerScrollListener: EndlessRecyclerOnScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_local_list, container, false)

        endlessRecyclerScrollListener = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                presenter.searchMore()
            }
        }

        fragmentView.recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = localAdapter
            addOnScrollListener(endlessRecyclerScrollListener)
        }

        return fragmentView
    }

    fun addLocalList(dataList: List<Document>) {
        localAdapter.addItem(dataList);
    }

    fun renewLocalList(dataList: List<Document>) {
        addLocalList(dataList)
        notifyDataSetChanged()
    }

    override fun getContext(): Context {
        return context
    }

    fun notifyDataSetChanged() {
        localAdapter.notifyDataSetChanged()
    }
}