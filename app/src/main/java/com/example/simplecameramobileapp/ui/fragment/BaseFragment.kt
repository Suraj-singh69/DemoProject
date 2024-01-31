package com.example.simplecameramobileapp.ui.fragment

//import androidx.fragment.app.viewModels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplecameramobileapp.R
import com.example.simplecameramobileapp.ui.common.BaseControlInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Base class for fragments that using databind feature to bind the view
 * also Implements [BaseControlInterface] interface
 * @param T A class that extends [ViewDataBinding] that will be used by the fragment layout binding view.
 * @param layoutId the resource layout view going to bind with the [binding] variable
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment(),
    BaseControlInterface
     {


    open lateinit var binding: T


    lateinit var  loadingDialog: AlertDialog

    //    val baseViewModel: BaseViewModel by viewModels()
    var loadbarCount=0


    private val INTERVAL = (1000 * 10).toLong()
    private val FASTEST_INTERVAL = (1000 * 5).toLong()

    private var locationPermission =false
    private var locationListener: LocationListener?=null
    private var locationUpdateListener: LocationListener?=null




    lateinit var loginUserId:String
    lateinit var loginUserType:String
    lateinit var baseURL:String
    lateinit var loginNameOfUser:String
    lateinit var ulbNameM:String

    lateinit var sp: SharedPreferences

    private var resolutionForResult: ActivityResultLauncher<IntentSenderRequest>? = null

    init {

        resolutionForResult = registerForActivityResult<IntentSenderRequest, ActivityResult>(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                //Granted
//                getLocation()
                Log.d("Loc_ON","Success")
            } else {
                //Not Granted
                Log.d("Loc_ON","Not Granted")
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        sp=requireContext().getSharedPreferences(getString(R.string.app_name), AppCompatActivity.MODE_PRIVATE)

        loadDataFromSP()
//        initLocationUpdateListener()
        return binding.root
    }
    private fun loadDataFromSP(){
//        val sp=requireContext().getSharedPreferences("",Context.MODE_PRIVATE)
        loginUserId=sp.getString("login_userName","")?:""
        loginUserType=sp.getString("login_userType","")?:""
        loginNameOfUser=sp.getString("login_nameOfUser","")?:""
        baseURL=sp.getString("BaseUrl","")?:""
        ulbNameM=sp.getString("ulbName","")?:""

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        addObservers()
//        setUpClicks()
        onInitialized()

    }

//    fun handlerLoadingAndError(base:BaseResponse<Any>){
//        when (base) {
//            is BaseResponse.Loading -> {
//               showLoading()
//            }
//            is BaseResponse.Error -> {
//                stopLoading()
//                processError(base.msg)
//            }
//            else -> {
//
//            }
//        }
//    }

    fun showLoading() {
        if(!loadingDialog.isShowing)
            loadingDialog.show()
    }

    fun stopLoading() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
    fun processError(msg: String?) {
        showToast("Error:$msg")
    }

    open fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

//    fun showWarningDialog(msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
//        customDialog(SweetAlertDialog.WARNING_TYPE,"Alert",msg,id,showCancelBtn,onDialogInterface)
//    }
//    fun showSuccessDialog(msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener){
//        customDialog(SweetAlertDialog.SUCCESS_TYPE,"Success",msg,id,showCancelBtn,onDialogInterface)
//    }
//    fun showErrorDialog(msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
//        customDialog(SweetAlertDialog.ERROR_TYPE,"Error",msg,id,showCancelBtn,onDialogInterface)
//    }
//
//    fun showDialog( msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
//        customDialog(SweetAlertDialog.NORMAL_TYPE,"Alert",msg,id,showCancelBtn,onDialogInterface)
//    }
//    fun showSimpleDialog( msg: String){
//        customDialog(SweetAlertDialog.NORMAL_TYPE,"Alert",msg,-1,false,null)
//    }
//
//    private fun customDialog(alertType:Int,title:String, msg: String,id:Int,showCancelBtn:Boolean,onDialogInterface: DialogInterface.OnClickListener?){
//        val dialog=SweetAlertDialog(requireContext(), alertType)
//            .setTitleText(title)
//            .setContentText(msg)
//            .setConfirmText("OK")
//            .setConfirmClickListener {
//                    sDialog -> sDialog.dismissWithAnimation()
//                if(onDialogInterface!=null) {
//                    onDialogInterface.onClick(sDialog, id)
//                }
//            }
//        if(showCancelBtn){
//            dialog.cancelText = "Cancel"
//            dialog.setCancelClickListener(){
//                    sDialog -> sDialog.dismissWithAnimation()
//            }
//        }
//        dialog.setCanceledOnTouchOutside(false)
//
//        dialog.show()
//    }
//    fun showSnackbar(view: View,message: String) :Snackbar{
//        val snackbar= Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
//        snackbar.show()
//        return snackbar
//    }
//
//    @SuppressLint("MissingPermission", "SetTextI18n")
//    private fun getLocation() {
//        if (isLocationEnabled(requireContext())) {
//            mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
//                val location: Location? = task.result
//                if (location != null) {
//                    if(locationListener!=null) {
//                        locationListener?.onLocationChanged(location)
//                    }
////                        val geocoder = Geocoder(this, Locale.getDefault())
////                        val list: List<Address> =
////                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
////                        mainBinding.apply {
////                            tvLatitude.text = "Latitude\n${list[0].latitude}"
////                            tvLongitude.text = "Longitude\n${list[0].longitude}"
////                            tvCountryName.text = "Country Name\n${list[0].countryName}"
////                            tvLocality.text = "Locality\n${list[0].locality}"
////                            tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
//                }
//            }
//        } else {
//            requestDeviceLocationSettings()
//            showToast("Please turn on location")
//        }
//    }
//    fun getCurrentLocation(onLocationChangeListener: LocationListener){
//        this.locationListener=onLocationChangeListener
//        checkPermissions()
//
//    }

//    @SuppressLint("MissingPermission")
//    fun startLocationUpdate(locationUpdate:LocationListener){
//        this.locationUpdateListener=locationUpdate
//        PermissionX.init(activity)
//            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//            .onExplainRequestReason { scope, deniedList ->
//                scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
//            }
//            .onForwardToSettings { scope, deniedList ->
//                scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
//            }
//            .request { allGranted, grantedList, deniedList ->
//                if (allGranted) {
//                    val locationRequest = LocationRequest.create()
//                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                        .setInterval(5000) // Update interval in milliseconds (e.g., every 5 seconds)
//                        .setFastestInterval(1000) // Fastest interval for updates (optional)
//
//                    LocationServices.getFusedLocationProviderClient(requireActivity())
//                        .requestLocationUpdates(locationRequest,locationCallback, null)
////                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
//                } else {
//                    showToast( "These permissions are denied: $deniedList")
//                }
//            }
//
//    }
//    private fun initLocationUpdateListener(){
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                // Handle location updates here
//                val locations = locationResult.locations
//                if (locations.isNotEmpty()) {
//                    val latestLocation = locations.last()
//                    // Do something with the latest location
//                    if (latestLocation.accuracy <= 20) {
//                        // Location is accurate, update the map or perform actions here
//                        if (locationUpdateListener != null) {
//                            locationUpdateListener?.onLocationChanged(latestLocation)
//                        }
//                    }
//                }
//            }
//        }
//    }
//    fun stopLocationUpdate(){
//        LocationServices.getFusedLocationProviderClient(requireActivity())
//            .removeLocationUpdates(locationCallback);
//    }
//    fun makeLocationOn():Boolean{
//        val b=isLocationEnabled(requireContext())
//        if(!b){
//            requestDeviceLocationSettings()
//        }
//        return b
//    }
//
//    private fun isLocationEnabled(context: Context): Boolean {
//        val locationManager: LocationManager =
//            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }
//
//    private fun checkPermissions() {
//        PermissionX.init(activity)
//            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//            .onExplainRequestReason { scope, deniedList ->
//                scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
//            }
//            .onForwardToSettings { scope, deniedList ->
//                scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
//            }
//            .request { allGranted, grantedList, deniedList ->
//                if (allGranted) {
//                    locationPermission=true
//                    getLocation()
////                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
//                } else {
//                    stopLoading()
//                    showToast( "These permissions are denied: $deniedList")
//                }
//            }
//    }
//
//    private fun requestDeviceLocationSettings() {
//        val mLocationRequest = LocationRequest()
//        mLocationRequest.setInterval(INTERVAL)
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL)
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//
//        val builder = LocationSettingsRequest.Builder()
//            .addLocationRequest(mLocationRequest)
//
//        // **************************
//
//        // **************************
//        builder.setAlwaysShow(true) // this is the key ingredient
//
//        // **************************
//        LocationServices.getSettingsClient(requireContext()).checkLocationSettings(builder.build())
//            .addOnSuccessListener(requireActivity()) { response: LocationSettingsResponse? ->
//
//            }
//            .addOnFailureListener(requireActivity()) { ex ->
//                if (ex is ResolvableApiException) {
//                    try {
//                        val intentSenderRequest =
//                            IntentSenderRequest.Builder((ex as ResolvableApiException).resolution)
//                                .build()
//                        resolutionForResult?.launch(intentSenderRequest)
//                    } catch (exception: Exception) {
//                        Log.d("TAG", "enableLocationSettings: $exception")
//                    }
//                }
//            }
//        // **************************
//
//
//    }
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        if (1022 == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                //user clicked OK, you can startUpdatingLocation(...);
                if(locationListener!=null){
                    locationListener?.onProviderEnabled("Yes")
                }
            } else {
                //user clicked cancel: informUserImportanceOfLocationAndPresentRequestAgain();
            }
        }
    }


}