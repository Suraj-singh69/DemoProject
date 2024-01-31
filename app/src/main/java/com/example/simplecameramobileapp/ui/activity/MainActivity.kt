package com.example.simplecameramobileapp.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.simplecameramobileapp.R
import com.example.simplecameramobileapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.IOException


class MainActivity : AppCompatActivity() , SurfaceHolder.Callback, PictureCallback{
    lateinit var binding: ActivityMainBinding
    private lateinit var surfaceHolder: SurfaceHolder
    private lateinit var surfaceView: SurfaceView
    private lateinit var camera: Camera
    private var status:String="1"
    var next_btn: String = ""
    var bitmap1: Bitmap? =null
    var bitmap2: Bitmap? =null
    var bitmap3: Bitmap? =null
    var bitmap4: Bitmap? =null
    var itemList:MutableList<Bitmap> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        surfaceView=binding.imgSurface
        binding.layTop.setBackgroundResource(R.drawable.zero_pos_select_bg)
        binding.layLeft.setBackgroundResource(R.drawable.rectangle_22)
        binding.layFrontal.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
        binding.layRight.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
        binding.userImg.setImageResource(R.drawable.img_top)

        binding.openCamera.setOnClickListener {
            binding.imgSurface.visibility=View.VISIBLE
            binding.userImg.visibility= View.GONE
            if (checkCameraPermission()){
//                camera=Camera.open()
                surfaceHolder = surfaceView.holder
                surfaceHolder.addCallback(this as SurfaceHolder.Callback)
                binding.openCamera.visibility=View.GONE
                binding.captureimg.visibility=View.VISIBLE
            }else{
                requestCameraPermission()
            }

        }


        binding.captureimg.setOnClickListener {
            if (camera != null) {
                if ( status.equals("1")){
                    camera!!.takePicture(null, null) { bytes, camera ->
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        bitmap1=bitmap
                        itemList.add(bitmap)
                        binding.imgSurface.visibility=View.GONE
                        binding.userImg.visibility= View.VISIBLE
                        binding.ivimg.visibility=View.VISIBLE
                        binding.cardLayout!!.visibility=View.VISIBLE
                        binding.ivimg.setImageBitmap(bitmap1)
                        binding.img1.setImageBitmap(bitmap1)
                        binding.openCamera.visibility=View.VISIBLE
                        binding.captureimg.visibility=View.GONE
                        releaseCamera()
                        next_btn="1"
                        binding.nextBtn.setImageResource(R.drawable.end_act)
                    }
                }
                if ( status.equals("2")){
                    camera!!.takePicture(null, null) { bytes, camera ->
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        bitmap2=bitmap
                        itemList.add(bitmap)
                        binding.imgSurface.visibility=View.GONE
                        binding.userImg.visibility= View.VISIBLE
                        binding.cardLayout!!.visibility=View.VISIBLE
                        binding.ivimg.visibility=View.VISIBLE
                        binding.ivimg.setImageBitmap(bitmap2)
                        binding.img2.setImageBitmap(bitmap2)
                        binding.openCamera.visibility=View.VISIBLE
                        binding.captureimg.visibility=View.GONE
                        releaseCamera()
                        binding.nextBtn.setImageResource(R.drawable.end_act)
                        next_btn="1"
                    }
                }
                if ( status.equals("3")){
                    camera!!.takePicture(null, null) { bytes, camera ->
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        bitmap3=bitmap
                        itemList.add(bitmap)
                        binding.imgSurface.visibility=View.GONE
                        binding.userImg.visibility= View.VISIBLE
                        binding.ivimg.visibility=View.VISIBLE
                        binding.cardLayout!!.visibility=View.VISIBLE
                        binding.ivimg.setImageBitmap(bitmap3)
                        binding.img3.setImageBitmap(bitmap3)
                        binding.openCamera.visibility=View.VISIBLE
                        binding.captureimg.visibility=View.GONE
                        releaseCamera()
                        binding.nextBtn.setImageResource(R.drawable.end_act)
                        next_btn="1"
                    }
                }
                if ( status.equals("4")){
                    camera!!.takePicture(null, null) { bytes, camera ->
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                        bitmap4=bitmap
                        itemList.add(bitmap)
                        binding.imgSurface.visibility=View.GONE
                        binding.userImg.visibility= View.VISIBLE
                        binding.cardLayout!!.visibility=View.VISIBLE
                        binding.ivimg.visibility=View.VISIBLE
                        binding.ivimg.setImageBitmap(bitmap4)
                        binding.img4.setImageBitmap(bitmap4)
                        binding.openCamera.visibility=View.VISIBLE
                        binding.captureimg.visibility=View.GONE
                        releaseCamera()
                        binding.nextBtn.setImageResource(R.drawable.end_act)

                        next_btn="1"
                    }
                }

            }
        }

        binding.nextBtn.setOnClickListener {
            if (next_btn.equals("")){
                Toast.makeText(this,"Please Upload Image..",Toast.LENGTH_SHORT).show()
            }else{
                val mygeson=Gson()
                var itemlist=mygeson.toJson(itemList)
                val intent=Intent(this,SelectImageActivity::class.java)
                intent.putExtra("list",itemlist)
                startActivity(intent)
            }
        }



        binding.layTop.setOnClickListener {
            if (bitmap1==null){
                status="1"
                binding.layTop.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.layLeft.setBackgroundResource(R.drawable.rectangle_22)
                binding.layFrontal.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layRight.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.userImg.setImageResource(R.drawable.img_top)
                binding.openCamera.visibility=View.VISIBLE
                binding.captureimg.visibility=View.GONE
                binding.ivimg.visibility=View.GONE
                binding.cardLayout!!.visibility=View.VISIBLE
                binding.userImg.visibility= View.VISIBLE
            }else{
                binding.ivimg.setImageBitmap(bitmap1)
                binding.img1.setImageBitmap(bitmap1)
                binding.ivimg.visibility= View.VISIBLE
                binding.cardLayout!!.visibility=View.VISIBLE
                binding.userImg.visibility= View.GONE
                binding.openCamera.visibility=View.GONE
                binding.captureimg.visibility=View.GONE
                binding.layTop.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.layLeft.setBackgroundResource(R.drawable.rectangle_22)
                binding.layFrontal.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layRight.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
            }

        }



        binding.layLeft.setOnClickListener {
            if (bitmap2==null){
                status="2"
                binding.layTop.setBackgroundResource(R.drawable.rectangle_22__2_)
                binding.layLeft.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.layFrontal.setBackgroundResource(R.drawable.rectangle_22__3_)
                binding.layRight.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.userImg.setImageResource(R.drawable.img_left)
                binding.openCamera.visibility=View.VISIBLE
                binding.captureimg.visibility=View.GONE
                binding.ivimg.visibility=View.GONE
                binding.cardLayout!!.visibility=View.GONE
                binding.userImg.visibility= View.VISIBLE
            }else{
                binding.ivimg.setImageBitmap(bitmap2)
                binding.img2.setImageBitmap(bitmap2)
                binding.ivimg.visibility= View.VISIBLE
                binding.cardLayout!!.visibility=View.VISIBLE
                binding.userImg.visibility= View.GONE
                binding.openCamera.visibility=View.GONE
                binding.captureimg.visibility=View.GONE
                binding.layTop.setBackgroundResource(R.drawable.rectangle_22__2_)
                binding.layLeft.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.layFrontal.setBackgroundResource(R.drawable.rectangle_22__3_)
                binding.layRight.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
            }
        }

        binding.layFrontal.setOnClickListener {
            if (bitmap3==null){
                status="3"
                binding.layTop.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layLeft.setBackgroundResource(R.drawable.rectangle_22__2_)
                binding.layFrontal.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.layRight.setBackgroundResource(R.drawable.rectangle_22__3_)
                binding.userImg.setImageResource(R.drawable.front)
                binding.openCamera.visibility=View.VISIBLE
                binding.captureimg.visibility=View.GONE
                binding.ivimg.visibility=View.GONE
                binding.cardLayout!!.visibility=View.GONE
                binding.userImg.visibility= View.VISIBLE
            }else{
                binding.ivimg.setImageBitmap(bitmap3)
                binding.img3.setImageBitmap(bitmap3)
                binding.ivimg.visibility= View.VISIBLE
                binding.cardLayout!!.visibility=View.VISIBLE
                binding.userImg.visibility= View.GONE
                binding.openCamera.visibility=View.GONE
                binding.captureimg.visibility=View.GONE
                binding.layTop.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layLeft.setBackgroundResource(R.drawable.rectangle_22__2_)
                binding.layFrontal.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.layRight.setBackgroundResource(R.drawable.rectangle_22__3_)
            }
        }

        binding.layRight.setOnClickListener {

            if (bitmap4==null){
                status="4"
                binding.layTop.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layLeft.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layFrontal.setBackgroundResource(R.drawable.rectangle_22__2_)
                binding.layRight.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.userImg.setImageResource(R.drawable.img_right)
                binding.openCamera.visibility=View.VISIBLE
                binding.captureimg.visibility=View.GONE
                binding.ivimg.visibility=View.GONE
                binding.cardLayout!!.visibility=View.GONE
                binding.userImg.visibility= View.VISIBLE
            }else{
                binding.ivimg.setImageBitmap(bitmap4)
                binding.img4.setImageBitmap(bitmap4)
                binding.ivimg.visibility= View.VISIBLE
                binding.cardLayout!!.visibility=View.VISIBLE
                binding.userImg.visibility= View.GONE
                binding.openCamera.visibility=View.GONE
                binding.captureimg.visibility=View.GONE
                binding.layTop.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layLeft.setBackgroundResource(R.drawable.zero_pos_unselect_bg)
                binding.layFrontal.setBackgroundResource(R.drawable.rectangle_22__2_)
                binding.layRight.setBackgroundResource(R.drawable.zero_pos_select_bg)
                binding.userImg.setImageResource(R.drawable.img_right)
            }
        }




    }
    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            1
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted, proceed with camera functionality
                    camera=Camera.open()
                } else {
                    // Camera permission denied, handle accordingly (e.g., show a message)
                    // You may want to provide more information to the user about why the permission is needed.
                }
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            // create the surface and start camera preview
            camera=Camera.open()
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            Log.d("VIEW_LOG_TAG", "Error setting camera preview: " + e.message)
        }

    }

    private fun startBackCamera() {
        camera = Camera.open()
        camera.setDisplayOrientation(90)
        try {
            camera.setPreviewDisplay(surfaceHolder)
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        resetCamera()

    }

    fun resetCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return
        }
        // stop preview before making changes
        // stop preview before making changes
        try {
            camera.stopPreview()
        } catch (e: Exception) {
            // ignore: tried to stop a non-existent preview
        }
        setCamera(camera)
        try {
            camera.setPreviewDisplay(surfaceHolder)
            camera.startPreview()
        } catch (e: Exception) {
            Log.d("VIEW_LOG_TAG", "Error starting camera preview: " + e.message)
        }


    }

    fun setCamera(mCamera: Camera) {
        //method to set a camera instance
        camera = mCamera
    }



    override fun surfaceDestroyed(holder: SurfaceHolder) {

        Log.i("PREVIEW","surfaceDestroyed")
    }

    private fun releaseCamera() {
        if (camera != null) {
            camera.release()
            camera==null

        }
    }

    override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
        Toast.makeText(this,"hiss",Toast.LENGTH_SHORT).show()
    }



    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        camera?.release()
    }

}