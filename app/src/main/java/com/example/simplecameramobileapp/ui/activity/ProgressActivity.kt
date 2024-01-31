package com.example.simplecameramobileapp.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.example.simplecameramobileapp.R
import com.example.simplecameramobileapp.databinding.ActivityProgressBinding
import com.example.simplecameramobileapp.databinding.ActivitySelectImageBinding
import com.example.simplecameramobileapp.databinding.DialogBinding
import com.example.simplecameramobileapp.ui.adapter.CustomAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.skydoves.progressview.OnProgressChangeListener

class ProgressActivity : AppCompatActivity() {
    lateinit var binding: ActivityProgressBinding
    var itemList:MutableList<Bitmap> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progress)
        binding.progressViewHorizontal.duration=3500L
        binding.progressViewHorizontal.setOnProgressChangeListener(OnProgressChangeListener { progress ->
            if(progress==100.0F){
                showDialog()
            }
        })
        binding.progressViewHorizontal.progressAnimate()
        Handler().postDelayed(Runnable {
            showDialog()
        },3500L)

    }
    private fun showDialog(){
        val dialog= AlertDialog.Builder(this, R.style.DialogCustomTheme)
        val binding= DialogBinding.inflate(layoutInflater)
        dialog.setView(binding.root)
//        dialog.wi.setBackgroundDrawableResource(android.R.color.transparent)
        val mDialog=dialog.create()
        binding.btnBack.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            mDialog.dismiss()
        }
        mDialog.setCancelable(true)
        mDialog.setCanceledOnTouchOutside(true)

        mDialog.show()

    }

}