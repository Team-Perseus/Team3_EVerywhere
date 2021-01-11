package com.example.everywhere


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.everywhere.models.Document
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*

open class SearchActivity : AppCompatActivity(),
    SearchLocalConstants.View {

    private val localListFragment: LocalListFragment by lazy { LocalListFragment() }
    var presenter: SearchLocalConstants.Presenter =
        SearchLocalPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        search_view.setOnClickListener { view ->
            val searchStr = search_view.query.toString()
            this.hideKeyboard(view)
            searchText(searchStr)
        }
        val localAdapter = RecyclerAdapter(presenter.getLocalArrList())
        localAdapter.setOnItemClickListener(object :
            RecyclerAdapter.ItemClickListener {
            override fun onClick(view: View, data: Document) {
                val bundle = Bundle()
                val jsonBody = Gson().toJson(data)
                bundle.putString("body", jsonBody)
                localListFragment.arguments = bundle
            }
        })
        localListFragment.presenter = presenter
        localListFragment.localAdapter = localAdapter

        addStackFragment(R.id.container_list, localListFragment, "list")
    }

    @Synchronized
    fun addStackFragment(resId: Int, fragment: Fragment, tag: String) {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            supportFragmentManager.beginTransaction()
                .add(resId, fragment).addToBackStack(tag)
                .commit()
        }
    }

    fun searchText(searchStr : String) {
        presenter.reset()
        presenter.onClear()
        presenter.callLocalList(searchStr)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            return
        }
        finish()
    }

    override fun renewLocalList(dataList: List<Document>) {
        localListFragment.renewLocalList(dataList)
    }

    override fun notifyDataSetChanged() {
        localListFragment.notifyDataSetChanged()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
