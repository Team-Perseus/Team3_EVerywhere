package com.example.everywhere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.recyclerview_item.*

class BookmarkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        /* if(intent.hasExtra("bookmark")){
           tv_item_placename.text= intent.getStringExtra("bookmark")
        }
        else{
            tv_item_addressname.text=intent.getStringExtra("bookmark")
        } */
        val no=intent.getStringExtra("no")


    }
}

