package com.example.wifip2pdirectdemo


import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import java.security.Permission

class WifiDirectActivity : AppCompatActivity() {
    val MY_TAG: String = "MY_TAG"
    val PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION = 100
    val PERMISSIONS_REQUEST_NEARBY_WIFI_DEVICES = 101

    private var channel: WifiP2pManager.Channel? = null
    private var manager: WifiP2pManager? = null
    private var receiver: BroadcastReceiver? = null


    private val intentFilter = IntentFilter()
    private var isWifiP2pEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        if (!initP2p()) {
            finish()
        }

//        startMultiplePermissionRequest(
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.NEARBY_WIFI_DEVICES
//            )
//        )
    }

    private fun initP2p(): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
            Log.e(MY_TAG, "Wi-Fi Direct is not supported by this device.")
            return false
        }

        val wifiManager: WifiManager? =
            this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager == null) {
            Log.e(MY_TAG, "Cannot get Wi-Fi system service.")
            return false
        }

        if (!wifiManager.isP2pSupported) {
            Log.e(MY_TAG, "Wi-Fi Direct is not supported by the hardware or Wi-Fi is off.")
            return false
        }

        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        if (manager == null) {
            Log.e(MY_TAG, "Cannot get Wi-Fi Direct system service.")
            return false
        }

        channel = manager!!.initialize(this, mainLooper, null)
        if (channel == null) {
            Log.e(MY_TAG, "Cannot initialize Wi-Fi Direct.")
            return false
        }
        return true
    }

    //RESET
    fun resetData() {

    }

    //PERMISSION
    /*    private val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Log.d(MY_TAG, "granted!")
            } else {
                // PERMISSION NOT GRANTED
                Log.d(MY_TAG, "Not granted!")
                finish()
            }
        }

        private fun startPermissionRequest(permission: String) {
            requestPermissionLauncher.launch(permission)
        }*/

//    private val requestMultiplePermissions = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        permissions.entries.forEach {
//            Log.d("DEBUG", "${it.key} = ${it.value}")
//            if (!it.value) {
//                finish()
//            }
//        }
//    }
//
//    private fun startMultiplePermissionRequest(multiplePermissions: Array<String>) {
//        requestMultiplePermissions.launch(multiplePermissions)
//    }


    //SET and GET
    fun setIsWifiP2pEnabled(isWifiP2pEnabled: Boolean) {
        this.isWifiP2pEnabled = isWifiP2pEnabled
    }

    /** register the BroadcastReceiver with the intent values to be matched  */
    public override fun onResume() {
        super.onResume()
        receiver = WiFiDirectBroadcastReceiver(manager!!, channel!!, this)
        registerReceiver(receiver, intentFilter)
    }

    public override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }
}