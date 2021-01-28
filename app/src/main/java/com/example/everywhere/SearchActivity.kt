package com.example.everywhere


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.everywhere.models.Document
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.recyclerview_item.*

open class SearchActivity : AppCompatActivity(),
    SearchLocalConstants.View {

    private val localListFragment: LocalListFragment by lazy { LocalListFragment() }
    var presenter: SearchLocalConstants.Presenter = SearchLocalPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        /* btn_bookmark.setOnClickListener{
            send_recyclerview.text=""
            if(btn_bookmark.isChecked)
                send_recyclerview.append("체크 박스 확인")
        }*/

        /* val nextIntent= Intent(this, BookmarkActivity::class.java)
        nextIntent.putExtra("bookmark", "btn_bookmark")
        startActivity(nextIntent)

        //저장된 데이터 로드
        loadData() */

        search_view.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchStr = search_view.query.toString()
                searchText(searchStr)
                return true
            }
        })
        val localAdapter = RecyclerAdapter(presenter.getLocalArrList())
        localAdapter.setOnItemClickListener(object :
            RecyclerAdapter.ItemClickListener {
            override fun onClick(view: View, data: Document) {
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
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
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


    /* private fun loadData(){
        val pref=getSharedPreferences("pref", 0)
        btn_bookmark.isChecked=pref.getBoolean("checked", false)
    }

    override fun onDestroy() { // 액티비티 종료될 때 호출
        super.onDestroy()

        saveData()
    }

    private fun saveData(){
        val pref=getSharedPreferences("pref", 0)
        val edit=pref.edit() //수정모드
        edit.putBoolean("checked", btn_bookmark.isChecked)
        edit.apply() //값을 저장
    } */

}
