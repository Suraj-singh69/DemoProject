package com.example.simplecameramobileapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simplecameramobileapp.R
import com.example.simplecameramobileapp.databinding.FragmentTakePictureBinding

 class TakePictureFragment :BaseFragment<FragmentTakePictureBinding>(R.layout.fragment_take_picture) {
    var fragNo=0
     companion object{
         fun getInstance(bundle: Bundle): Fragment {
             val f=TakePictureFragment()
             f.arguments=bundle
             return f
         }
     }
     override fun onInitialized() {
         if(arguments!=null){
             fragNo=requireArguments().getInt("fragNo")?:0
         }
         changeUiAccordingFrag()
     }

     override fun setUpClicks() {

     }
     fun changeUiAccordingFrag(){
         when(fragNo){
             0->{

             }
             1->{

             }
             2->{

             }
         }
     }

}