package com.example.simplecameramobileapp.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.simplecameramobileapp.R
import com.example.simplecameramobileapp.databinding.ActivitySelectImageBinding
import com.example.simplecameramobileapp.ui.adapter.CustomAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SelectImageActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectImageBinding
    var itemList:MutableList<Bitmap> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_image)

        var item=intent.getStringExtra("list").toString()

        val imageInfoList = Gson().fromJson<List<Bitmap>>(
            item,
            object : TypeToken<List<Bitmap>>() {}.type
        )
        binding.rcv.adapter=CustomAdapter(this,imageInfoList)
        binding.btnUpload.setOnClickListener {
            val intent= Intent(this,ProgressActivity::class.java)
            startActivity(intent)
        }

    }
}